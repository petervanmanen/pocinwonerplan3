import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './verblijfadresingeschrevennatuurlijkpersoon.reducer';

export const VerblijfadresingeschrevennatuurlijkpersoonDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const verblijfadresingeschrevennatuurlijkpersoonEntity = useAppSelector(state => state.verblijfadresingeschrevennatuurlijkpersoon.entity);
  const updateSuccess = useAppSelector(state => state.verblijfadresingeschrevennatuurlijkpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/verblijfadresingeschrevennatuurlijkpersoon');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(verblijfadresingeschrevennatuurlijkpersoonEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="verblijfadresingeschrevennatuurlijkpersoonDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="demo3App.verblijfadresingeschrevennatuurlijkpersoon.delete.question">
        Are you sure you want to delete Verblijfadresingeschrevennatuurlijkpersoon {verblijfadresingeschrevennatuurlijkpersoonEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button
          id="jhi-confirm-delete-verblijfadresingeschrevennatuurlijkpersoon"
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

export default VerblijfadresingeschrevennatuurlijkpersoonDeleteDialog;
