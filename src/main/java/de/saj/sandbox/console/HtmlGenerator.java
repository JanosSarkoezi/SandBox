package de.saj.sandbox.console;

import com.google.gson.Gson;
import de.saj.sandbox.console.container.ElementComposite;
import de.saj.sandbox.console.element.A;
import de.saj.sandbox.console.element.Body;
import de.saj.sandbox.console.element.Div;
import de.saj.sandbox.console.element.H1;
import de.saj.sandbox.console.element.H2;
import de.saj.sandbox.console.element.H3;
import de.saj.sandbox.console.element.Head;
import de.saj.sandbox.console.element.Html;
import de.saj.sandbox.console.element.Li;
import de.saj.sandbox.console.element.Link;
import de.saj.sandbox.console.element.Meta;
import de.saj.sandbox.console.element.Script;
import de.saj.sandbox.console.element.Table;
import de.saj.sandbox.console.element.Tbody;
import de.saj.sandbox.console.element.Td;
import de.saj.sandbox.console.element.Title;
import de.saj.sandbox.console.element.Tr;
import de.saj.sandbox.console.element.Ul;
import de.saj.sandbox.console.sprint.BackBone;
import de.saj.sandbox.console.sprint.StoryMapping;
import de.saj.sandbox.console.sprint.SubTask;
import de.saj.sandbox.console.sprint.UserTask;
import de.saj.sandbox.console.visitor.FindVisitor;
import de.saj.sandbox.console.visitor.PrintVisitor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author saj
 */
public class HtmlGenerator {

    private HtmlGenerator() {
    }

    public static void main(String[] args) throws Exception {
        HtmlGenerator generator = new HtmlGenerator();
        generator.generateHTML();
    }

    private void generateHTML() throws IOException, URISyntaxException {
        Html html = new Html();

        // @formatter:off
        html.add(new Head().newLine()
                .add(new Meta().attribute("charset", "utf-8"))
                .add(new Title().content("Test"))
                .add(new Link().attribute("rel", "stylesheet").attribute("href", "css/style.css"))
                .add(new Link().attribute("rel", "stylesheet").attribute("href", "lightbox2/css/lightbox.css")))
                .add(new Body().newLine()
                        .add(new Table()
                                .add(new Tbody("tablebody").newLine()
                                        .add(new Tr("backbone").newLine())
                                        .add(new Tr("userTask").newLine())
                                )
                        )
                        .add(new Script().attribute("src", "lightbox2/js/lightbox-plus-jquery.js"))
                        .add(new Script().content(createJqueryScript()))
                ).newLine();
        // @formatter:on

        FindVisitor backboneVisitor = new FindVisitor("backbone");
        html.accept(backboneVisitor);
        Tr backboneTr = (Tr) backboneVisitor.getElement();

        FindVisitor userTaskVisitor = new FindVisitor("userTask");
        html.accept(userTaskVisitor);
        Tr userTaskTr = (Tr) userTaskVisitor.getElement();

        FindVisitor bodyVisitor = new FindVisitor("tablebody");
        html.accept(bodyVisitor);
        Tbody tbody = (Tbody) bodyVisitor.getElement();

        String content = getJson("email.json");
        StoryMapping storyMapping = new Gson().fromJson(content, StoryMapping.class);

        fillBackbondeTr(storyMapping, backboneTr);
        fillUserTaskTr(storyMapping, userTaskTr);
        fillSubTastTrs(storyMapping, tbody);

        html.accept(new PrintVisitor());
    }

    private String createJqueryScript() {
        return "$(document).ready(function() {\n" +
                "    $('.information').click(function() {\n" +
                "        $('.menu').slideToggle(\"fast\");\n" +
                "    });\n" +
                "});";
    }

    private String getJson(String fileName) throws URISyntaxException, IOException {
        return new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(fileName).toURI())));
    }

    private void fillBackbondeTr(StoryMapping storyMapping, Tr backboneTr) {
        List<BackBone> backbones = storyMapping.getBackbone();
        for (BackBone backbone : backbones) {
            // @formatter:off
            backboneTr.add(new Td()
                    .add(new Div().attribute("class", "note backbone")
                            .add(new H1().content(backbone.getTitle()))
                    )
            );
            // @formatter:on

            List<UserTask> userTasks = backbone.getUserTasks();
            for (int i = 0; i < userTasks.size() - 1; i++) {
                backboneTr.add(new Td());
            }
        }
    }

    private void fillUserTaskTr(StoryMapping storyMapping, Tr userTaskTr) {
        List<BackBone> backbones = storyMapping.getBackbone();

        for (BackBone backbone : backbones) {
            List<UserTask> userTasks = backbone.getUserTasks();

            for (UserTask userTask : userTasks) {
                // @formatter:off
                userTaskTr.add(new Td()
                        .add(new Div().attribute("class", "note usertask")
                                .add(new H2().content(userTask.getTitle()))
                        )
                );
                // @formatter:on
            }
        }
    }

    private void fillSubTastTrs(StoryMapping storyMapping, Tbody tbody) {
        Map<Integer, Tr> positionToTrMapping = new HashMap<>();

        List<BackBone> backbones = storyMapping.getBackbone();
        for (BackBone backbone : backbones) {
            processBackbone(backbone, positionToTrMapping);
        }

        Collection<Tr> values = positionToTrMapping.values();
        for (Tr value : values) {
            tbody.add(value);
        }
    }

    private void processBackbone(BackBone backbone, Map<Integer, Tr> positionToTrMapping) {
        List<UserTask> userTasks = backbone.getUserTasks();
        for (UserTask userTask : userTasks) {
            processUserTask(userTask, positionToTrMapping);
        }
    }

    private void processUserTask(UserTask userTask, Map<Integer, Tr> positionToTrMapping) {
        List<SubTask> subTasks = userTask.getSubTasks();
        for (SubTask subTask : subTasks) {
            processSubTask(subTask, positionToTrMapping);
        }
    }

    private void processSubTask(SubTask subTask, Map<Integer, Tr> positionToTrMapping) {
        Integer position = subTask.getPosition();
        Tr subTaskTr = positionToTrMapping.get(position);

        if (subTaskTr == null) {
            subTaskTr = new Tr("subTask" + position);

            positionToTrMapping.put(position, subTaskTr);
        }

        Td subTaskTd = new Td();
        createSubTaskDiv(subTaskTd, subTask);
        subTaskTr.add(subTaskTd.newLine());
    }

    private void createSubTaskDiv(Td subTaskTd, SubTask subTask) {
        if (subTask.getTitle() != null) {
            ElementComposite subTaskDiv = new Div().attribute("class", "note subtask");

            // @formatter:off
            subTaskTd.add(subTaskDiv
                    .add(new H3().content(subTask.getTitle()))
                    .add(new Div().content(subTask.getContent()))
            );
            // @formatter:on

            if (subTask.getInformation() != null) {
                ElementComposite menuDiv = new Div().attribute("class", "menu")
                        .attribute("style", "display: none;");

                subTaskDiv.add(new Div().attribute("class", "information").content("i"))
                        .add(menuDiv);

                addImages(menuDiv, subTask);
                addLinks(menuDiv, subTask);
                addToDos(menuDiv, subTask);
            }

            if (subTask.getDone() != null) {
                ElementComposite done = new Div().attribute("class", "done");

                if (subTask.getDone()) {
                    done.attribute("style", "background-color: green;");
                } else {
                    done.attribute("style", "background-color: red;");
                }

                subTaskDiv.add(done);
            }
        }
    }

    private void addImages(ElementComposite menuDiv, SubTask subTask) {
        List<String> images = subTask.getInformation().getImages();
        if (images.isEmpty()) {
            return;
        }

        Ul ul = new Ul();
        menuDiv.add(new H3().content("Bilder"))
                .add(ul);

        for (String image : images) {
            ul.add(new Li().add(new A().attribute("href", image).attribute("data-lightbox", "image").content(image)));
        }
    }

    private void addLinks(ElementComposite menuDiv, SubTask subTask) {
        List<String> links = subTask.getInformation().getLinks();
        if (links.isEmpty()) {
            return;
        }

        Ul ul = new Ul();
        menuDiv.add(new H3().content("Links"))
                .add(ul);

        for (String link : links) {
            ul.add(new Li().add(new A().attribute("href", link).content(link)));
        }
    }

    private void addToDos(ElementComposite menuDiv, SubTask subTask) {
        List<String> todos = subTask.getInformation().getTodos();
        if (todos.isEmpty()) {
            return;
        }

        Ul ul = new Ul();
        menuDiv.add(new H3().content("ToDo"))
                .add(ul);

        for (String todo : todos) {
            ul.add(new Li().content(todo));
        }
    }
}
