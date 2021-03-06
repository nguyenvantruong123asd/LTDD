package vn.edu.topedu.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vn.edu.topedu.entity.ActiveAccount;
import vn.edu.topedu.entity.AppUser;
import vn.edu.topedu.entity.CategoryEntity;
import vn.edu.topedu.entity.Payment;
import vn.edu.topedu.entity.RequestResetPassword;
import vn.edu.topedu.entity.ResourceImage;
import vn.edu.topedu.entity.course.BaseCourse;
import vn.edu.topedu.entity.course.full.VideoEntity;
import vn.edu.topedu.fileprocess.FileProcess;

@Repository
@Transactional
public class ResourceImageDAO {

    @Autowired
    private EntityManager entityManager;

    public ResourceImage findById(Long id) {
        return this.entityManager.find(ResourceImage.class, id);
    }

    public ResourceImage findFirst() {
        String sql = "Select e from " + ResourceImage.class.getName() + " e "
                + " where e.deleted=false  ";
        Query query = this.entityManager.createQuery(sql, ResourceImage.class);
        query.setFirstResult(1);
        query.setMaxResults(1);
        return (ResourceImage) query.getSingleResult();
    }

    public List<ResourceImage> getResourceImages(Long userId) {
        String sql = "Select e from " + ResourceImage.class.getName() + " e "
                + " where e.deleted=false and e.appUser.id =:userId ";
        Query query = this.entityManager.createQuery(sql, ResourceImage.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<ResourceImage> getResourceImages(int _limit, int _page) {
        _page--;
        String sql = "Select e from " + ResourceImage.class.getName() + " e " + " where e.deleted=false ";
        Query query = this.entityManager.createQuery(sql, ResourceImage.class);
        query.setFirstResult(_page * _limit);
        if (_limit != -1) {
            query.setMaxResults(_limit);
        }

        return query.getResultList();
    }

    public long getCountResourceImages() {
        String sql = "Select count(e.id) from " + ResourceImage.class.getName() + " e " + " where e.deleted=false ";
        Query query = this.entityManager.createQuery(sql, Long.class);
        return (long) query.getSingleResult();
    }

    public List<ResourceImage> getResourceImages(String userName) {
        String sql = "Select e from " + ResourceImage.class.getName() + " e "
                + " where e.deleted=false and e.appUser.userName =:userName ";
        Query query = this.entityManager.createQuery(sql, ResourceImage.class);
        query.setParameter("userName", userName);
        return query.getResultList();
    }

    public List<ResourceImage> getResourceImages() {
        String sql = "Select e from " + ResourceImage.class.getName() + " e " + " where e.deleted=false  ";
        Query query = this.entityManager.createQuery(sql, ResourceImage.class);
        return query.getResultList();
    }

    public ResourceImage save(ResourceImage image) {
        if (image.getId() != null) {
            return entityManager.merge(image);
        } else {
            entityManager.persist(image);
            entityManager.flush();
            return image;
        }
    }

    public ResourceImage deleteImage(long id) {
        ResourceImage deleted = findById(id);
        deleted.setDeleted(true);
        entityManager.merge(deleted);
        entityManager.flush();
        return deleted;

    }

    public List<ResourceImage> getResourceImagesNoLinked(int deleted) {
        String sql = "Select e from " + ResourceImage.class.getName() + " e "
                + " where e.countLinked=0   ";
        if (deleted != -1) sql += " and e.deleted= :deleted ";
        Query query = this.entityManager.createQuery(sql, ResourceImage.class);
        if (deleted != -1) query.setParameter("deleted", deleted == 1);
        return query.getResultList();
    }

    @Transactional
    public int deleteAllNoLink(int deleted) throws Exception {
        try {

            List<ResourceImage> a = getResourceImagesNoLinked(deleted);
            for (ResourceImage ri : a) {
                String str = ri.getAbsPath();
                System.err.println(str);
                if (str != null
                        && (str.contains("http") || str.contains("data:image"))) {
                    continue;
                }
                Path path = FileProcess.getPath(ri.getAbsPath());
                //if(path.to)
                if (path.toFile().delete()) {
                    System.err.println(String.format("Delete file: %s", path.toString()));


                }
                ;
            }
            String sql = "delete from " + ResourceImage.class.getName() + " e where e.countLinked=0 ";
            if (deleted != -1) sql += " and e.deleted= :deleted ";
            Query query = this.entityManager.createQuery(sql);
            if (deleted != -1) query.setParameter("deleted", deleted == 1);


            return query.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }


    }

    @Transactional
    public ResourceImage uploadImage(MultipartFile uploadAvatar, AppUser appUser, boolean deleted) throws Exception {
        String userName = (appUser != null) ? appUser.getUsername() : "default";
        ResourceImage newAvatar = null;
        if (uploadAvatar != null) {
            System.err.println("Avatar upload");

            String pathContain = null;
            try {
                ResourceImage image = new ResourceImage();
                image.setDeleted(deleted);
                pathContain = String.format("user/%s/image", userName);
                String originImage = uploadAvatar.getOriginalFilename();
                originImage = originImage.replaceAll("[^0-9a-zA-Z\\.]", "_");
                image.setPath(pathContain + "/" + originImage);
                image.setAppUser(appUser);
                newAvatar = save(image);

                pathContain = String.format("user/%s/image", userName);

                String filename = newAvatar.getAbsPath();
                System.out.println(String.format("File: %s", originImage));
                File p = FileProcess.getPath(filename).toFile();
                System.out.println(p.getAbsolutePath());
                p.getParentFile().mkdirs();
                InputStream initialStream = uploadAvatar.getInputStream();
                OutputStream outStream = new FileOutputStream(p);
                byte[] buffer = new byte[8 * 1024];
                int bytesRead;
                while ((bytesRead = initialStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
                IOUtils.closeQuietly(initialStream);
                IOUtils.closeQuietly(outStream);


                System.err.println("Upload Success");
            } catch (Exception e) {
                System.err.println(e.getMessage());
                throw e;
            }
        }
        return newAvatar;
    }

}
