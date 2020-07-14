package org.uma.jmetal.lab.visualization.html.componentsImpl;

import org.uma.jmetal.lab.visualization.html.*;

public class HtmlTable<T> implements HtmlComponent {

  protected String title;
  protected String[] headersColumn;
  protected String[] headersRow;
  protected T[][] data;

  public HtmlTable<T> setTitle(String title) {

    this.title = title;

    return this;
  }

  public HtmlTable<T> setColumnHeaders(String[] headers) {

    if (data[0].length != headers.length) return this;

    this.headersColumn = headers;

    return this;
  }

  public HtmlTable<T> setRowHeaders(String[] headers) {

    if (data.length != headers.length) return this;

    this.headersRow = headers;

    return this;
  }

  public HtmlTable<T> setData(T[][] data) {
    this.data = data;
    return this;
  }

  public String getHtml() {

    StringBuilder html = new StringBuilder("<div>\n");

    html.append("<table>\n");

    html.append(appendTitle());

    html.append(appendColumnHeaders());

    html.append(appendData());

    html.append("</table>\n</div>\n");

    return html.toString();
  }

  private StringBuilder appendTitle() {

    StringBuilder sb = new StringBuilder();

    if (title != null) {

      sb.append("<caption>").append(title).append("</caption>\n");
    }

    return sb;
  }

  private StringBuilder appendColumnHeaders() {

    StringBuilder sb = new StringBuilder();

    sb.append("<tr>");

    //FIRST CELL EMPTY IF THERE ARE ROW HEADERS
    if (headersRow != null) {

      sb.append("<th>").append("</th>");
    }

    if (headersColumn != null) {

      for (String elem : headersColumn) {

        sb.append("<th>").append(elem).append("</th>");
      }
    }

    sb.append("</tr>\n");

    return sb;
  }

  private StringBuilder appendData() {

    StringBuilder html = new StringBuilder();

    for (int i = 0; i < data.length; i++) {

      html.append("<tr>");

      if (headersRow != null) {

        html.append("<th>").append(headersRow[i]).append("</th>");
      }

      html.append(createRowOfData(i));

      html.append("</tr>\n");
    }

    return html;
  }

  protected StringBuilder createRowOfData(int index) {

    StringBuilder html = new StringBuilder();

    for (T elem : data[index]) {

      html.append("<td>").append(elem.toString()).append("</td>");
    }

    return html;
  }

  public String getCSS() {

    StringBuilder sb = new StringBuilder();

    sb.append("table { margin: auto; }");

    sb.append("th,td { border:1px solid black; text-align: center; padding: 15px }");

    sb.append(
        "caption { display: table-caption; text-align: center; margin: 10px; font-size: 1.5em; }");

    return sb.toString();
  }
}
