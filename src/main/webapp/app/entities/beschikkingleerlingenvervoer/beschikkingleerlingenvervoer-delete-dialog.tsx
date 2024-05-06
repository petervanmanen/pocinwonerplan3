import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './beschikkingleerlingenvervoer.reducer';

export const BeschikkingleerlingenvervoerDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const beschikkingleerlingenvervoerEntity = useAppSelector(state => state.beschikkingleerlingenvervoer.entity);
  const updateSuccess = useAppSelector(state => state.beschikkingleerlingenvervoer.updateSuccess);

  const handleClose = () => {
    navigate('/beschikkingleerlingenvervoer');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(beschikkingleerlingenvervoerEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="beschikkingleerlingenvervoerDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="demo3App.beschikkingleerlingenvervoer.delete.question">
        Are you sure you want to delete Beschikkingleerlingenvervoer {beschikkingleerlingenvervoerEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button
          id="jhi-confirm-delete-beschikkingleerlingenvervoer"
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

export default BeschikkingleerlingenvervoerDeleteDialog;
