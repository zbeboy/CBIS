package com.school.cbis.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by lenovo on 2016-04-26.
 */
public class JsGridData {
    private final Logger log = LoggerFactory.getLogger(JsGridData.class);

    private Number number;
    private Text text;
    private Select select;
    private SelectData selectData;
    private Checkbox checkbox;
    private Control control;

    public Number getNumber() {
        number = new Number();
        return number;
    }

    public Text getText() {
        text = new Text();
        return text;
    }

    public Select getSelect() {
        select = new Select();
        return select;
    }

    public SelectData getSelectData() {
        selectData = new SelectData();
        return selectData;
    }

    public Checkbox getCheckbox() {
        checkbox = new Checkbox();
        return checkbox;
    }

    public Control getControl() {
        control = new Control();
        return control;
    }

    public String numberString(Number number) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("\"name\":" + "\"" + number.getName() + "\",");
        stringBuilder.append("\"title\":" + "\"" + number.getTitle() + "\",");
        stringBuilder.append("\"type\":" + "\"" + number.getType() + "\",");
        stringBuilder.append("\"width\":" + "\"" + number.getWidth() + "\",");
        stringBuilder.append("\"filtering\":" + "\"" + number.isFiltering() + "\",");
        stringBuilder.append("\"inserting\":" + "\"" + number.isInserting() + "\",");
        stringBuilder.append("\"editing\":" + "\"" + number.isEditing() + "\",");
        stringBuilder.append("\"sorting\":" + "\"" + number.isSorting() + "\",");
        stringBuilder.append("\"visible\":" + "\"" + number.isVisible() + "\"");
        stringBuilder.append("}");
        log.debug(" numberJson : {}" + stringBuilder.toString());
        return stringBuilder.toString();
    }

    public String textString(Text text) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("\"name\":" + "\"" + text.getName() + "\",");
        stringBuilder.append("\"title\":" + "\"" + text.getTitle() + "\",");
        stringBuilder.append("\"type\":" + "\"" + text.getType() + "\",");
        stringBuilder.append("\"visible\":" + "\"" + text.isVisible() + "\",");
        stringBuilder.append("\"filtering\":" + "\"" + text.isFiltering() + "\",");
        stringBuilder.append("\"inserting\":" + "\"" + text.isInserting() + "\",");
        stringBuilder.append("\"editing\":" + "\"" + text.isEditing() + "\",");
        stringBuilder.append("\"sorting\":" + "\"" + text.isSorting() + "\",");
        stringBuilder.append("\"width\":" + "\"" + text.getWidth() + "\"");
        stringBuilder.append("}");
        log.debug(" textJson : {}" + stringBuilder.toString());
        return stringBuilder.toString();
    }

    public String selectString(Select select) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("\"name\":" + "\"" + select.getName() + "\",");
        stringBuilder.append("\"title\":" + "\"" + select.getTitle() + "\",");
        stringBuilder.append("\"type\":" + "\"" + select.getType() + "\",");
        stringBuilder.append("\"items\":" + select.getItems() + ",");
        stringBuilder.append("\"valueField\":" + "\"" + select.getValueField() + "\",");
        stringBuilder.append("\"textField\":" + "\"" + select.getTextField() + "\",");
        stringBuilder.append("\"filtering\":" + "\"" + select.isFiltering() + "\",");
        stringBuilder.append("\"inserting\":" + "\"" + select.isInserting() + "\",");
        stringBuilder.append("\"editing\":" + "\"" + select.isEditing() + "\",");
        stringBuilder.append("\"sorting\":" + "\"" + select.isSorting() + "\",");
        stringBuilder.append("\"width\":" + "\"" + select.getWidth() + "\"");
        stringBuilder.append("}");
        log.debug(" selectJson : {}" + stringBuilder.toString());
        return stringBuilder.toString();
    }

    public String selectDataString(List<SelectData> selectDatas) {
        StringBuilder stringBuilder = new StringBuilder();
        if(!selectDatas.isEmpty()&&selectDatas.size()>1){
            stringBuilder.append("{\"Name\":\"\",\"Id\":\"0\"},");
        } else {
            stringBuilder.append("{\"Name\":\"\",\"Id\":\"0\"}");
        }
        String temp = "[";
        for (SelectData selectData : selectDatas) {
            stringBuilder.append("{")
                    .append("\"Name\":\"" + selectData.getName() + "\",")
                    .append("\"Id\":\"" + selectData.getId() + "\"},");
        }
        temp = temp + stringBuilder.toString().substring(0, stringBuilder.toString().lastIndexOf(",")) + "]";
        log.debug(" selectData : {}" + temp);
        return temp;
    }

    public String checkboxString(Checkbox checkbox) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("\"name\":" + "\"" + checkbox.getName() + "\",");
        stringBuilder.append("\"title\":" + "\"" + checkbox.getTitle() + "\",");
        stringBuilder.append("\"type\":" + "\"" + checkbox.getType() + "\",");
        stringBuilder.append("\"visible\":" + "\"" + checkbox.isVisible() + "\",");
        stringBuilder.append("\"sorting\":" + "\"" + checkbox.isSorting() + "\",");
        stringBuilder.append("\"filtering\":" + "\"" + checkbox.isFiltering() + "\",");
        stringBuilder.append("\"inserting\":" + "\"" + checkbox.isInserting() + "\",");
        stringBuilder.append("\"editing\":" + "\"" + checkbox.isEditing() + "\",");
        stringBuilder.append("\"width\":" + "\"" + checkbox.getWidth() + "\"");
        stringBuilder.append("}");
        log.debug(" checkboxJson : {}" + stringBuilder.toString());
        return stringBuilder.toString();
    }

    public String controlString(Control control){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("\"modeSwitchButton\":" + "\"" + control.isModeSwitchButton() + "\",");
        stringBuilder.append("\"editButton\":" + "\"" + control.isEditButton() + "\",");
        stringBuilder.append("\"type\":" + "\"" + control.getType() + "\",");
        stringBuilder.append("\"deleteButton\":" + "\"" + control.isDeleteButton() + "\",");
        stringBuilder.append("\"clearFilterButton\":" + "\"" + control.isClearFilterButton() + "\",");
        stringBuilder.append("\"filtering\":" + "\"" + control.isFiltering() + "\",");
        stringBuilder.append("\"inserting\":" + "\"" + control.isInserting() + "\",");
        stringBuilder.append("\"editing\":" + "\"" + control.isEditing() + "\",");
        stringBuilder.append("\"sorting\":" + "\"" + control.isSorting() + "\"");
        stringBuilder.append("}");
        log.debug(" checkboxJson : {}" + stringBuilder.toString());
        return stringBuilder.toString();
    }

    public String getFieldsSet(List<String> s) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp = "";
        for (String t : s) {
            stringBuilder.append(t + ",");
        }
        temp = "["+stringBuilder.toString().substring(0, stringBuilder.toString().lastIndexOf(","))+"]";
        log.debug(" fieldJson : {}" + temp);
        return temp;
    }

    /**
     * number type
     */
    public class Number {
        private String name;
        private String title;
        private String type = "number";
        private boolean visible;
        private int width;
        private boolean filtering = false;
        private boolean inserting = false;
        private boolean editing = false;
        private boolean sorting = false;

        public Number() {
        }

        public Number(String name, String title, String type, boolean visible, int width, boolean filtering, boolean inserting, boolean editing, boolean sorting) {
            this.name = name;
            this.title = title;
            this.type = type;
            this.visible = visible;
            this.width = width;
            this.filtering = filtering;
            this.inserting = inserting;
            this.editing = editing;
            this.sorting = sorting;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public boolean isFiltering() {
            return filtering;
        }

        public void setFiltering(boolean filtering) {
            this.filtering = filtering;
        }

        public boolean isInserting() {
            return inserting;
        }

        public void setInserting(boolean inserting) {
            this.inserting = inserting;
        }

        public boolean isEditing() {
            return editing;
        }

        public void setEditing(boolean editing) {
            this.editing = editing;
        }

        public boolean isSorting() {
            return sorting;
        }

        public void setSorting(boolean sorting) {
            this.sorting = sorting;
        }
    }

    /**
     * text type
     */
    public class Text {
        private String name;
        private String title;
        private String type = "text";
        private boolean visible;
        private boolean filtering = false;
        private boolean inserting = false;
        private boolean editing = false;
        private boolean sorting = false;
        private int width;

        public Text() {
        }

        public Text(String name, String title, String type, boolean visible, boolean filtering, boolean inserting, boolean editing, boolean sorting, int width) {
            this.name = name;
            this.title = title;
            this.type = type;
            this.visible = visible;
            this.filtering = filtering;
            this.inserting = inserting;
            this.editing = editing;
            this.sorting = sorting;
            this.width = width;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public boolean isFiltering() {
            return filtering;
        }

        public void setFiltering(boolean filtering) {
            this.filtering = filtering;
        }

        public boolean isInserting() {
            return inserting;
        }

        public void setInserting(boolean inserting) {
            this.inserting = inserting;
        }

        public boolean isEditing() {
            return editing;
        }

        public void setEditing(boolean editing) {
            this.editing = editing;
        }

        public boolean isSorting() {
            return sorting;
        }

        public void setSorting(boolean sorting) {
            this.sorting = sorting;
        }
    }

    /**
     * select type
     */
    public class Select {
        private String name;
        private String title;
        private String type = "select";
        private String items;
        private String valueField;
        private String textField;
        private int width;
        private boolean filtering = false;
        private boolean inserting = false;
        private boolean editing = false;
        private boolean sorting = false;

        public Select() {
        }

        public Select(String name, String title, String type, String items, String valueField, String textField, int width, boolean filtering, boolean inserting, boolean editing, boolean sorting) {
            this.name = name;
            this.title = title;
            this.type = type;
            this.items = items;
            this.valueField = valueField;
            this.textField = textField;
            this.width = width;
            this.filtering = filtering;
            this.inserting = inserting;
            this.editing = editing;
            this.sorting = sorting;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getItems() {
            return items;
        }

        public void setItems(String items) {
            this.items = items;
        }

        public String getValueField() {
            return valueField;
        }

        public void setValueField(String valueField) {
            this.valueField = valueField;
        }

        public String getTextField() {
            return textField;
        }

        public void setTextField(String textField) {
            this.textField = textField;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public boolean isFiltering() {
            return filtering;
        }

        public void setFiltering(boolean filtering) {
            this.filtering = filtering;
        }

        public boolean isInserting() {
            return inserting;
        }

        public void setInserting(boolean inserting) {
            this.inserting = inserting;
        }

        public boolean isEditing() {
            return editing;
        }

        public void setEditing(boolean editing) {
            this.editing = editing;
        }

        public boolean isSorting() {
            return sorting;
        }

        public void setSorting(boolean sorting) {
            this.sorting = sorting;
        }
    }

    /**
     * select data
     */
    public class SelectData {
        private String Name;
        private int Id;

        public SelectData() {
        }

        public SelectData(String name, int id) {
            Name = name;
            Id = id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }
    }

    /**
     * checkbox
     */
    public class Checkbox {
        private String name;
        private String title;
        private String type = "checkbox";
        private boolean visible;
        private boolean filtering = false;
        private boolean inserting = false;
        private boolean editing = false;
        private boolean sorting = false;
        private int width;

        public Checkbox() {
        }

        public Checkbox(String name, String title, String type, boolean visible, boolean filtering, boolean inserting, boolean editing, boolean sorting, int width) {
            this.name = name;
            this.title = title;
            this.type = type;
            this.visible = visible;
            this.filtering = filtering;
            this.inserting = inserting;
            this.editing = editing;
            this.sorting = sorting;
            this.width = width;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public boolean isFiltering() {
            return filtering;
        }

        public void setFiltering(boolean filtering) {
            this.filtering = filtering;
        }

        public boolean isInserting() {
            return inserting;
        }

        public void setInserting(boolean inserting) {
            this.inserting = inserting;
        }

        public boolean isEditing() {
            return editing;
        }

        public void setEditing(boolean editing) {
            this.editing = editing;
        }

        public boolean isSorting() {
            return sorting;
        }

        public void setSorting(boolean sorting) {
            this.sorting = sorting;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }

    /**
     * control
     */
    public class Control {
        private String type = "control";
        private boolean modeSwitchButton;
        private boolean editButton;
        private boolean deleteButton;
        private boolean clearFilterButton;
        private boolean filtering = false;
        private boolean inserting = false;
        private boolean editing = false;
        private boolean sorting = false;
        private int width;

        public Control() {
        }

        public Control(String type, boolean modeSwitchButton, boolean editButton, boolean deleteButton, boolean clearFilterButton, boolean filtering, boolean inserting, boolean editing, boolean sorting, int width) {
            this.type = type;
            this.modeSwitchButton = modeSwitchButton;
            this.editButton = editButton;
            this.deleteButton = deleteButton;
            this.clearFilterButton = clearFilterButton;
            this.filtering = filtering;
            this.inserting = inserting;
            this.editing = editing;
            this.sorting = sorting;
            this.width = width;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isModeSwitchButton() {
            return modeSwitchButton;
        }

        public void setModeSwitchButton(boolean modeSwitchButton) {
            this.modeSwitchButton = modeSwitchButton;
        }

        public boolean isEditButton() {
            return editButton;
        }

        public void setEditButton(boolean editButton) {
            this.editButton = editButton;
        }

        public boolean isDeleteButton() {
            return deleteButton;
        }

        public void setDeleteButton(boolean deleteButton) {
            this.deleteButton = deleteButton;
        }

        public boolean isClearFilterButton() {
            return clearFilterButton;
        }

        public void setClearFilterButton(boolean clearFilterButton) {
            this.clearFilterButton = clearFilterButton;
        }

        public boolean isFiltering() {
            return filtering;
        }

        public void setFiltering(boolean filtering) {
            this.filtering = filtering;
        }

        public boolean isInserting() {
            return inserting;
        }

        public void setInserting(boolean inserting) {
            this.inserting = inserting;
        }

        public boolean isEditing() {
            return editing;
        }

        public void setEditing(boolean editing) {
            this.editing = editing;
        }

        public boolean isSorting() {
            return sorting;
        }

        public void setSorting(boolean sorting) {
            this.sorting = sorting;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }
}
