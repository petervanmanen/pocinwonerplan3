import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITegenprestatiehoogte } from 'app/shared/model/tegenprestatiehoogte.model';
import { getEntity, updateEntity, createEntity, reset } from './tegenprestatiehoogte.reducer';

export const TegenprestatiehoogteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tegenprestatiehoogteEntity = useAppSelector(state => state.tegenprestatiehoogte.entity);
  const loading = useAppSelector(state => state.tegenprestatiehoogte.loading);
  const updating = useAppSelector(state => state.tegenprestatiehoogte.updating);
  const updateSuccess = useAppSelector(state => state.tegenprestatiehoogte.updateSuccess);

  const handleClose = () => {
    navigate('/tegenprestatiehoogte');
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
      ...tegenprestatiehoogteEntity,
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
          ...tegenprestatiehoogteEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.tegenprestatiehoogte.home.createOrEditLabel" data-cy="TegenprestatiehoogteCreateUpdateHeading">
            Create or edit a Tegenprestatiehoogte
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
                <ValidatedField name="id" required readOnly id="tegenprestatiehoogte-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Code" id="tegenprestatiehoogte-code" name="code" data-cy="code" type="text" />
              <ValidatedField label="Naam" id="tegenprestatiehoogte-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="tegenprestatiehoogte-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tegenprestatiehoogte" replace color="info">
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

export default TegenprestatiehoogteUpdate;
