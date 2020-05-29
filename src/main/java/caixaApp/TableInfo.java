package caixaApp;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableInfo{
  private SimpleStringProperty id , title , date , value; //wrapper of String

  public TableInfo(String id , String title , String date , String value){
    this.id = new SimpleStringProperty(id);
    this.title = new SimpleStringProperty(title);
    this.date = new SimpleStringProperty(date);
    this.value = new SimpleStringProperty(value);
  }


  public void setId(String value) { idProperty().set(value); }
  public String getId() { return idProperty().get(); } /*PropertyValueFactory look for this*/
  public StringProperty idProperty(){
    if(id == null) id = new SimpleStringProperty(this , "id");
    return id;
  }

  public void setTitle(String value) { titleProperty().set(value); }
  public String getTitle() { return titleProperty().get(); } /*PropertyValueFactory look for this*/
  public StringProperty titleProperty(){
    if(title == null) title = new SimpleStringProperty(this , "title");
    return title;
  }

  public void setDate(String value) { dateProperty().set(value); }
  public String getDate() { return dateProperty().get(); } /*PropertyValueFactory look for this*/
  public StringProperty dateProperty(){
    if(date == null) date = new SimpleStringProperty(this , "date");
    return date;
  }

  public void setValue(String value) { valueProperty().set(value); }
  public String getValue() { return valueProperty().get(); } /*PropertyValueFactory look for this*/
  public StringProperty valueProperty(){
    if(this.value == null) this.value = new SimpleStringProperty(this , "value");
    return this.value;
  }

}
