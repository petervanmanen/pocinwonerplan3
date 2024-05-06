import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './geboorteingeschrevennatuurlijkpersoon.reducer';

export const GeboorteingeschrevennatuurlijkpersoonDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const geboorteingeschrevennatuurlijkpersoonEntity = useAppSelector(state => state.geboorteingeschrevennatuurlijkpersoon.entity);
  const updateSuccess = useAppSelector(state => state.geboorteingeschrevennatuurlijkpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/geboorteingeschrevennatuurlijkpersoon');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(geboorteingeschrevennatuurlijkpersoonEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="geboorteingeschrevennatuurlijkpersoonDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="demo3App.geboorteingeschrevennatuurlijkpersoon.delete.question">
        Are you sure you want to delete Geboorteingeschrevennatuurlijkpersoon {geboorteingeschrevennatuurlijkpersoonEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button
          id="jhi-confirm-delete-geboorteingeschrevennatuurlijkpersoon"
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

export default GeboorteingeschrevennatuurlijkpersoonDeleteDialog;
