import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './nederlandsenationaliteitingeschrevenpersoon.reducer';

export const NederlandsenationaliteitingeschrevenpersoonDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const nederlandsenationaliteitingeschrevenpersoonEntity = useAppSelector(
    state => state.nederlandsenationaliteitingeschrevenpersoon.entity,
  );
  const updateSuccess = useAppSelector(state => state.nederlandsenationaliteitingeschrevenpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/nederlandsenationaliteitingeschrevenpersoon');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(nederlandsenationaliteitingeschrevenpersoonEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="nederlandsenationaliteitingeschrevenpersoonDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="demo3App.nederlandsenationaliteitingeschrevenpersoon.delete.question">
        Are you sure you want to delete Nederlandsenationaliteitingeschrevenpersoon {nederlandsenationaliteitingeschrevenpersoonEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button
          id="jhi-confirm-delete-nederlandsenationaliteitingeschrevenpersoon"
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

export default NederlandsenationaliteitingeschrevenpersoonDeleteDialog;
