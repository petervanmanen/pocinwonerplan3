import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './ondersteunendwaterdeel.reducer';

export const OndersteunendwaterdeelDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const ondersteunendwaterdeelEntity = useAppSelector(state => state.ondersteunendwaterdeel.entity);
  const updateSuccess = useAppSelector(state => state.ondersteunendwaterdeel.updateSuccess);

  const handleClose = () => {
    navigate('/ondersteunendwaterdeel');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(ondersteunendwaterdeelEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="ondersteunendwaterdeelDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="demo3App.ondersteunendwaterdeel.delete.question">
        Are you sure you want to delete Ondersteunendwaterdeel {ondersteunendwaterdeelEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button id="jhi-confirm-delete-ondersteunendwaterdeel" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Delete
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default OndersteunendwaterdeelDeleteDialog;
