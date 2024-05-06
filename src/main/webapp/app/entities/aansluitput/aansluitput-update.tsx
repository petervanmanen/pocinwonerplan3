import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAansluitput } from 'app/shared/model/aansluitput.model';
import { getEntity, updateEntity, createEntity, reset } from './aansluitput.reducer';

export const AansluitputUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aansluitputEntity = useAppSelector(state => state.aansluitput.entity);
  const loading = useAppSelector(state => state.aansluitput.loading);
  const updating = useAppSelector(state => state.aansluitput.updating);
  const updateSuccess = useAppSelector(state => state.aansluitput.updateSuccess);

  const handleClose = () => {
    navigate('/aansluitput');
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
      ...aansluitputEntity,
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
          ...aansluitputEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aansluitput.home.createOrEditLabel" data-cy="AansluitputCreateUpdateHeading">
            Create or edit a Aansluitput
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="aansluitput-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aansluitpunt" id="aansluitput-aansluitpunt" name="aansluitpunt" data-cy="aansluitpunt" type="text" />
              <ValidatedField label="Risicogebied" id="aansluitput-risicogebied" name="risicogebied" data-cy="risicogebied" type="text" />
              <ValidatedField label="Type" id="aansluitput-type" name="type" data-cy="type" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aansluitput" replace color="info">
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

export default AansluitputUpdate;
