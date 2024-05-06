import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRioleringsgebied } from 'app/shared/model/rioleringsgebied.model';
import { getEntity, updateEntity, createEntity, reset } from './rioleringsgebied.reducer';

export const RioleringsgebiedUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rioleringsgebiedEntity = useAppSelector(state => state.rioleringsgebied.entity);
  const loading = useAppSelector(state => state.rioleringsgebied.loading);
  const updating = useAppSelector(state => state.rioleringsgebied.updating);
  const updateSuccess = useAppSelector(state => state.rioleringsgebied.updateSuccess);

  const handleClose = () => {
    navigate('/rioleringsgebied');
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
      ...rioleringsgebiedEntity,
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
          ...rioleringsgebiedEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.rioleringsgebied.home.createOrEditLabel" data-cy="RioleringsgebiedCreateUpdateHeading">
            Create or edit a Rioleringsgebied
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
                <ValidatedField name="id" required readOnly id="rioleringsgebied-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Rioleringsgebied"
                id="rioleringsgebied-rioleringsgebied"
                name="rioleringsgebied"
                data-cy="rioleringsgebied"
                type="text"
              />
              <ValidatedField
                label="Zuiveringsgebied"
                id="rioleringsgebied-zuiveringsgebied"
                name="zuiveringsgebied"
                data-cy="zuiveringsgebied"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rioleringsgebied" replace color="info">
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

export default RioleringsgebiedUpdate;
