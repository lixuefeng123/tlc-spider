package cn.com.fero.tlc.spider.http;

import cn.com.fero.tlc.spider.exception.TLCSpiderParserException;
import cn.com.fero.tlc.spider.exception.TLCSpiderRequestException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gizmo on 15/6/18.
 */
public class TLCSpiderHTMLParser {

     public static List<TagNode> parseNode(String content, String xpath) {
          try {
               HtmlCleaner htmlCleaner = new HtmlCleaner();
               TagNode html = htmlCleaner.clean(content);
               Object[] result = html.evaluateXPath(xpath);

               List<TagNode> tagNodeList = new ArrayList();
               for(Object obj : result) {
                    tagNodeList.add((TagNode)obj);
               }

               return tagNodeList;
          } catch (Exception e) {
               throw new TLCSpiderParserException(e);
          }
     }

     public static String parserText(String content, String xpath) {
          try {
               HtmlCleaner htmlCleaner = new HtmlCleaner();
               TagNode html = htmlCleaner.clean(content);
               Object[] result = html.evaluateXPath(xpath);
               TagNode tagNode = (TagNode)result[0];
               return tagNode.getText().toString();
          } catch (Exception e) {
               throw new TLCSpiderParserException(e);
          }
     }

     public static String parserText(TagNode tagNode, String xpath) {
          try {
               return tagNode.evaluateXPath(xpath).toString();
          } catch (Exception e) {
               throw new TLCSpiderParserException(e);
          }
     }


     public static String parseAttribute(String content, String xpath, String attribute) {
          try {
               HtmlCleaner htmlCleaner = new HtmlCleaner();
               TagNode html = htmlCleaner.clean(content);
               Object[] result = html.evaluateXPath(xpath);
               TagNode tagNode = (TagNode)result[0];
               return tagNode.getAttributeByName(attribute);
          } catch (Exception e) {
               throw new TLCSpiderParserException(e);
          }
     }

     public static String parseAttribute(TagNode tagNode, String xpath, String attribute) {
          try {
               return tagNode.getAttributeByName(attribute);
          } catch (Exception e) {
               throw new TLCSpiderParserException(e);
          }
     }
}
