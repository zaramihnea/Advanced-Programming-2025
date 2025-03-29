<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Image Repository Report</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            color: #333;
        }
        h1, h2 {
            color: #2c3e50;
        }
        .header {
            background-color: #f4f4f4;
            padding: 20px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .image-container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
        }
        .image-card {
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 15px;
            width: 300px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .image-card h3 {
            margin-top: 0;
            color: #3498db;
        }
        .tag {
            display: inline-block;
            background-color: #e0f7fa;
            color: #0288d1;
            padding: 3px 10px;
            border-radius: 15px;
            font-size: 0.8em;
            margin-right: 5px;
            margin-bottom: 5px;
        }
        .meta {
            color: #7f8c8d;
            font-size: 0.9em;
        }
        .footer {
            margin-top: 30px;
            text-align: center;
            font-size: 0.8em;
            color: #7f8c8d;
            border-top: 1px solid #eee;
            padding-top: 20px;
        }
    </style>
</head>
<body>
<div class="header">
    <h1>Image Repository Report</h1>
    <p>Total images: ${repositorySize}</p>
    <p class="meta">Generated on: ${generationDate}</p>
</div>

<#if images?size == 0>
    <div>
        <h2>No images in repository</h2>
        <p>The repository is currently empty.</p>
    </div>
<#else>
    <h2>Images</h2>
    <div class="image-container">
        <#list images as image>
            <div class="image-card">
                <h3>${image.name()}</h3>
                <p><strong>Date:</strong> ${image.date()}</p>
                <p><strong>Location:</strong> ${image.location()}</p>
                <p>
                    <strong>Tags:</strong><br>
                    <#if image.tags()?size == 0>
                        <span class="meta">No tags</span>
                    <#else>
                        <#list image.tags() as tag>
                            <span class="tag">${tag}</span>
                        </#list>
                    </#if>
                </p>
            </div>
        </#list>
    </div>
</#if>

<div class="footer">
    <p>Image Management System &copy; 2025</p>
</div>
</body>
</html>