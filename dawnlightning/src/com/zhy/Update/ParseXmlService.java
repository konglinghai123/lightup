package com.zhy.Update;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class ParseXmlService
{
    public HashMap<String, String> parseXml(InputStream inStream) throws Exception
    {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        
        // 实例化一个文档构建器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 通过文档构建器工厂获取一个文档构建器
        DocumentBuilder builder = factory.newDocumentBuilder();
        // 通过文档通过文档构建器构建一个文档实例
        Document document = builder.parse(inStream);
        //获取XML文件根节点
        Element root = document.getDocumentElement();
        
     
        //获得所有子节点root.getElementsByTagName("TabMenu");
        NodeList childNodes = root.getElementsByTagName("Android");
        NodeList nextchildnodes=childNodes.item(0).getChildNodes();
       
     
        //Log.e("version", nextchildnodes.item(0).getNodeValue());
      
       
        for (int j = 0; j < nextchildnodes.getLength(); j++)
        {
            //遍历子节点
            Node childNode = (Node) nextchildnodes.item(j);
     
            if (childNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element childElement = (Element) childNode;
                //版本号
                if ("version".equals(childElement.getNodeName()))
                {	
                    hashMap.put("version",childElement.getFirstChild().getNodeValue());
                }
                //软件名称
                else if (("name".equals(childElement.getNodeName())))
                {	 
                    hashMap.put("name",childElement.getFirstChild().getNodeValue());
                }
                //下载地址
                else if (("url".equals(childElement.getNodeName())))
                {	
                    hashMap.put("url",childElement.getFirstChild().getNodeValue());
                }else if(("note".equals(childElement.getNodeName()))){
                	hashMap.put("note",childElement.getFirstChild().getNodeValue());
                }
            }
        }
        return hashMap;
    }
    /**
     * 获取软件版本号
     * 
     * @param context
     * @return
     */
    private int getVersionCode(Context context)
    {
        int versionCode = 0;
        try
        {
            // 获取软件版本号，
            versionCode = context.getPackageManager().getPackageInfo("com.szy.update", 0).versionCode;
        } catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionCode;
    }
}