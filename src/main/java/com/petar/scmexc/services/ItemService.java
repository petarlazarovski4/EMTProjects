package com.marco.scmexc.services;

import com.marco.scmexc.models.domain.Item;
import com.marco.scmexc.models.domain.Question;
import com.marco.scmexc.models.domain.SmxFile;
import com.marco.scmexc.models.domain.Type;
import com.marco.scmexc.models.exceptions.ItemNotFoundException;
import com.marco.scmexc.models.requests.ItemRequest;
import com.marco.scmexc.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {


    private final ItemRepository itemRepository;
    private final QuestionRepository questionRepository;
    private final SmxFileRepository fileRepository;
    private final AnswerRepository answerRepository;

    public ItemService(ItemRepository itemRepository, QuestionRepository questionRepository, SmxFileRepository fileRepository, AnswerRepository answerRepository) {
        this.itemRepository = itemRepository;
        this.questionRepository = questionRepository;
        this.fileRepository = fileRepository;
        this.answerRepository = answerRepository;
    }

    public List<Item> getItemsByMaterial(Long materialID) {
        // exception
       return this.itemRepository.findAllByMaterial_Id(materialID);
    }

    public void deleteItemByID(Long itemID) {
        Item item = this.itemRepository.findById(itemID).orElseThrow(()-> new ItemNotFoundException(itemID));
        itemRepository.delete(item);
        if(item.getType()==Type.QUESTION) {
            Question question =item.getQuestion();
            answerRepository.deleteAll(question.getAnswers());
            questionRepository.delete(question);
        }
        else {
            fileRepository.delete(item.getSmxFile());
        }
    }

    public Item editItem(ItemRequest itemRequest) {
        //exception to add
        Item item = this.itemRepository.findById(itemRequest.itemID).orElseThrow(()-> new ItemNotFoundException(itemRequest.itemID));
        if(item.getType()==Type.QUESTION) {
            Question question = item.getQuestion();
            question.setDescription(itemRequest.description);
            item.setQuestion(question);
        }
        else {
            SmxFile file = item.getSmxFile();
            String extension = file.getFileName().split("\\.")[file.getFileName().split("\\.").length-1];
            String fileName = itemRequest.fileName +"."+extension;
            file.setFileName(fileName);
            item.setSmxFile(file);
        }
        return itemRepository.save(item);
    }

    public Item getItemByID(Long itemID){
        Item item = this.itemRepository.findById(itemID).orElseThrow(()->new ItemNotFoundException(itemID));
        return item;
    }

    public List<Item> getAllFilesByMaterial(Long materialID) {
        return this.itemRepository.findAllByMaterial_IdAndType(materialID,Type.FILE);
    }
    public List<Item> getAllImagesByMaterial(Long materialID) {
        return this.itemRepository.findAllByMaterial_IdAndType(materialID,Type.IMAGE);
    }
    public List<Item> findAllQuestionsByMaterial(Long materialID) {
        return this.itemRepository.findAllByMaterial_IdAndType(materialID,Type.QUESTION);
    }




}
