import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEnumenumerationa } from 'app/shared/model/enumenumerationa.model';
import { getEntity, updateEntity, createEntity, reset } from './enumenumerationa.reducer';

export const EnumenumerationaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const enumenumerationaEntity = useAppSelector(state => state.enumenumerationa.entity);
  const loading = useAppSelector(state => state.enumenumerationa.loading);
  const updating = useAppSelector(state => state.enumenumerationa.updating);
  const updateSuccess = useAppSelector(state => state.enumenumerationa.updateSuccess);

  const handleClose = () => {
    navigate('/enumenumerationa');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...enumenumerationaEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...enumenumerationaEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.enumenumerationa.home.createOrEditLabel" data-cy="EnumenumerationaCreateUpdateHeading">
            Create or edit a Enumenumerationa
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="enumenumerationa-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Optie 1" id="enumenumerationa-optie1" name="optie1" data-cy="optie1" type="text" />
              <ValidatedField label="Optie 2" id="enumenumerationa-optie2" name="optie2" data-cy="optie2" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/enumenumerationa" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EnumenumerationaUpdate;
