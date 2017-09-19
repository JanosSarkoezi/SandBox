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
import de.saj.sandbox.console.sprint.Information;
import de.saj.sandbox.console.sprint.StoryMapping;
import de.saj.sandbox.console.sprint.SubTask;
import de.saj.sandbox.console.sprint.UserTask;
import de.saj.sandbox.console.visitor.FindVisitor;
import de.saj.sandbox.console.visitor.PrintVisitor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author saj
 */
public class HtmlGenerator {

    private int informationCounter = 0;

    private HtmlGenerator() {
    }

    public static void main(String[] args) throws Exception {
        Options options = new Options();

        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output", true, "output file");
        output.setRequired(true);
        options.addOption(output);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("usm", options);

            System.exit(1);
            return;
        }

        String inputFilePath = cmd.getOptionValue("input");
        String outputFilePath = cmd.getOptionValue("output");

        HtmlGenerator generator = new HtmlGenerator();
        generator.generateHTML(inputFilePath, outputFilePath);
    }

    private void generateHTML(String inputFilePath, String outputFilePath) throws IOException, URISyntaxException {
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
                        .add(new Script().attribute("src", "js/jquery-1.12.4.js"))
                        .add(new Script().attribute("src", "js/jquery-ui-1.12.4.js"))
                        .add(new Script().attribute("src", "lightbox2/js/lightbox.js"))
                ).newLine();
        // @formatter:on

        FindVisitor backboneVisitor = new FindVisitor("backbone");
        html.accept(backboneVisitor);
        Tr backboneTr = (Tr) backboneVisitor.getElement();

        FindVisitor userTaskVisitor = new FindVisitor("userTask");
        html.accept(userTaskVisitor);
        Tr userTaskTr = (Tr) userTaskVisitor.getElement();

        FindVisitor tablebodyVisitor = new FindVisitor("tablebody");
        html.accept(tablebodyVisitor);
        Tbody tbody = (Tbody) tablebodyVisitor.getElement();

        String content = getJson(inputFilePath);
        StoryMapping storyMapping = new Gson().fromJson(content, StoryMapping.class);

        fillBackbondeTr(storyMapping, backboneTr);
        fillUserTaskTr(storyMapping, userTaskTr);
        fillSubTastTrs(storyMapping, tbody);

        FindVisitor bodyVisitor = new FindVisitor(Body.class);
        html.accept(bodyVisitor);
        Body body = (Body) bodyVisitor.getElement();

        body.add(new Script().content(createJqueryScript()));

        html.accept(new PrintVisitor(new PrintStream(new File(outputFilePath))));
    }

    private String createJqueryScript() {
//        StringBuilder buffer = new StringBuilder();
//        buffer.append("$(document).ready(function() {\n");
//
//        for (int i = 0; i < informationCounter; i++) {
//            buffer.append("    $('.a").append(i).append("').click(function() {\n");
//            buffer.append("        $('.b").append(i).append("').slideToggle(\"fast\");\n");
//            buffer.append("    });\n");
//        }
//
//        buffer.append("});");
//
//        return buffer.toString();
        String jquery = "$(document).ready(function() {\n" +
                "        $(document).ready(function() {\n" +
                "            $('.information').click((ev) => {\n" +
                "                let n = $(ev.target).next();\n" +
                "                let v = n.attr('data-expanded') == 'true';\n" +
                "                n.attr('data-expanded', !v);\n" +
                "                $(n).position({\n" +
                "                    of: $(n).parent()\n" +
                "                });\n" +
                "            });\n" +
                "        });\n" +
                "});";

        return jquery;
    }

    private String getJson(String fileName) throws URISyntaxException, IOException {
        Path path = Paths.get(fileName);
        return new String(Files.readAllBytes(path));
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

            handleInformation(subTask, subTaskDiv);
            handleDone(subTask, subTaskDiv);
        }
    }

    private void handleDone(SubTask subTask, ElementComposite subTaskDiv) {
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

    private void handleInformation(SubTask subTask, ElementComposite subTaskDiv) {
        Information information = subTask.getInformation();
        if (information != null) {
            ElementComposite menuDiv = new Div().attribute("class", "menu")
                    .attribute("data-expanded", "false");

            String indicator = "";
            if (!information.getImages().isEmpty()) {
                indicator += "i";
            }

            if (!information.getLinks().isEmpty()) {
                indicator += "l";
            }

            if (!information.getTodos().isEmpty()) {
                indicator += "t";
            }

            subTaskDiv.add(new Div().attribute("class", "information").content(indicator))
                    .add(menuDiv);

            addImages(menuDiv, subTask);
            addLinks(menuDiv, subTask);
            addToDos(menuDiv, subTask);
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
