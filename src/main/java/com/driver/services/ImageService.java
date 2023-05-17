package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog
        Image image = new Image();
        Blog blog = blogRepository2.findById(blogId).get();

        image.setBlog(blog);
        image.setDescription(description);
        image.setDimensions(dimensions);

        blog.getImageList().add(image);
        blogRepository2.save(blog);

        return image;
    }

    public void deleteImage(Integer id){
        Image image = imageRepository2.findById(id).get();

        if(image != null) {
            Blog blog = image.getBlog();
            blog.getImageList().remove(image);
            blogRepository2.save(blog);

            imageRepository2.delete(image);
        }
    }

    public int[] findFirstDimension(String dimensions) {
        String number = "";
        int [] ansArray = new int [2];

        for(int i = 0; i < dimensions.length(); i++) {
            if(dimensions.charAt(i) == 'X') {
                ansArray[0] = i;
                break;
            }

            number += dimensions.charAt(i);
        }

        ansArray[1] = Integer.parseInt(number);
        return ansArray;
    }

    public int findSecond(int index, String dimensions) {
        String number = "";

        for (int i = index; i < dimensions.length(); i++) {
            char ch = dimensions.charAt(i);

            if (ch != 'X') number += ch;
        }

        return Integer.parseInt(number);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        Image image = imageRepository2.findById(id).get();
        String dimensions = image.getDimensions();

        int [] arrayWithFirstScreenDimension = findFirstDimension(screenDimensions);
        int indexForSecondScreenDimension = arrayWithFirstScreenDimension[0];
        int firstScreenDimension = arrayWithFirstScreenDimension[1];
        int secondScreenDimension = findSecond(indexForSecondScreenDimension, screenDimensions);

        int screenArea = firstScreenDimension * secondScreenDimension;

        int [] arrayWithFirstImageDimension = findFirstDimension(dimensions);
        int indexForSecondImageDimension = arrayWithFirstImageDimension[0];
        int firstImageDimension = arrayWithFirstImageDimension[1];
        int secondImageDimension = findSecond(indexForSecondImageDimension, dimensions);

        int singleImageArea = firstImageDimension * secondImageDimension;

        return screenArea / singleImageArea;
    }
}
