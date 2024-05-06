import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.reducer';

export const SluitingofaangaanhuwelijkofgeregistreerdpartnerschapDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const sluitingofaangaanhuwelijkofgeregistreerdpartnerschapEntity = useAppSelector(
    state => state.sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.entity,
  );
  const updateSuccess = useAppSelector(state => state.sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.updateSuccess);

  const handleClose = () => {
    navigate('/sluitingofaangaanhuwelijkofgeregistreerdpartnerschap');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(sluitingofaangaanhuwelijkofgeregistreerdpartnerschapEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="sluitingofaangaanhuwelijkofgeregistreerdpartnerschapDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="demo3App.sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.delete.question">
        Are you sure you want to delete Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap{' '}
        {sluitingofaangaanhuwelijkofgeregistreerdpartnerschapEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button
          id="jhi-confirm-delete-sluitingofaangaanhuwelijkofgeregistreerdpartnerschap"
          data-cy="entityConfirmDeleteButton"
          color="danger"
          onClick={confirmDelete}
        >
          <FontAwesomeIcon icon="trash" />
          &nbsp; Delete
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default SluitingofaangaanhuwelijkofgeregistreerdpartnerschapDeleteDialog;
