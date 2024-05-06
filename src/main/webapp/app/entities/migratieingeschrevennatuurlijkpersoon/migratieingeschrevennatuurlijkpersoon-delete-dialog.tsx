import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './migratieingeschrevennatuurlijkpersoon.reducer';

export const MigratieingeschrevennatuurlijkpersoonDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const migratieingeschrevennatuurlijkpersoonEntity = useAppSelector(state => state.migratieingeschrevennatuurlijkpersoon.entity);
  const updateSuccess = useAppSelector(state => state.migratieingeschrevennatuurlijkpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/migratieingeschrevennatuurlijkpersoon');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(migratieingeschrevennatuurlijkpersoonEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="migratieingeschrevennatuurlijkpersoonDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="demo3App.migratieingeschrevennatuurlijkpersoon.delete.question">
        Are you sure you want to delete Migratieingeschrevennatuurlijkpersoon {migratieingeschrevennatuurlijkpersoonEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button
          id="jhi-confirm-delete-migratieingeschrevennatuurlijkpersoon"
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

export default MigratieingeschrevennatuurlijkpersoonDeleteDialog;
