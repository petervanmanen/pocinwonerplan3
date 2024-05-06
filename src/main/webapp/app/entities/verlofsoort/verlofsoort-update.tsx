import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerlofsoort } from 'app/shared/model/verlofsoort.model';
import { getEntity, updateEntity, createEntity, reset } from './verlofsoort.reducer';

export const VerlofsoortUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verlofsoortEntity = useAppSelector(state => state.verlofsoort.entity);
  const loading = useAppSelector(state => state.verlofsoort.loading);
  const updating = useAppSelector(state => state.verlofsoort.updating);
  const updateSuccess = useAppSelector(state => state.verlofsoort.updateSuccess);

  const handleClose = () => {
    navigate('/verlofsoort');
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
      ...verlofsoortEntity,
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
          ...verlofsoortEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verlofsoort.home.createOrEditLabel" data-cy="VerlofsoortCreateUpdateHeading">
            Create or edit a Verlofsoort
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="verlofsoort-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Naam" id="verlofsoort-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="verlofsoort-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verlofsoort" replace color="info">
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

export default VerlofsoortUpdate;
