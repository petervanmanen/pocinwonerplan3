import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAardaantekening } from 'app/shared/model/aardaantekening.model';
import { getEntity, updateEntity, createEntity, reset } from './aardaantekening.reducer';

export const AardaantekeningUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aardaantekeningEntity = useAppSelector(state => state.aardaantekening.entity);
  const loading = useAppSelector(state => state.aardaantekening.loading);
  const updating = useAppSelector(state => state.aardaantekening.updating);
  const updateSuccess = useAppSelector(state => state.aardaantekening.updateSuccess);

  const handleClose = () => {
    navigate('/aardaantekening');
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
      ...aardaantekeningEntity,
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
          ...aardaantekeningEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aardaantekening.home.createOrEditLabel" data-cy="AardaantekeningCreateUpdateHeading">
            Create or edit a Aardaantekening
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
                <ValidatedField name="id" required readOnly id="aardaantekening-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Codeaardaantekening"
                id="aardaantekening-codeaardaantekening"
                name="codeaardaantekening"
                data-cy="codeaardaantekening"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheidaardaantekening"
                id="aardaantekening-datumbegingeldigheidaardaantekening"
                name="datumbegingeldigheidaardaantekening"
                data-cy="datumbegingeldigheidaardaantekening"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidaardaantekening"
                id="aardaantekening-datumeindegeldigheidaardaantekening"
                name="datumeindegeldigheidaardaantekening"
                data-cy="datumeindegeldigheidaardaantekening"
                type="date"
              />
              <ValidatedField
                label="Naamaardaantekening"
                id="aardaantekening-naamaardaantekening"
                name="naamaardaantekening"
                data-cy="naamaardaantekening"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aardaantekening" replace color="info">
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

export default AardaantekeningUpdate;
