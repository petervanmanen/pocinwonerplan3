import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBergingsbassin } from 'app/shared/model/bergingsbassin.model';
import { getEntity, updateEntity, createEntity, reset } from './bergingsbassin.reducer';

export const BergingsbassinUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bergingsbassinEntity = useAppSelector(state => state.bergingsbassin.entity);
  const loading = useAppSelector(state => state.bergingsbassin.loading);
  const updating = useAppSelector(state => state.bergingsbassin.updating);
  const updateSuccess = useAppSelector(state => state.bergingsbassin.updateSuccess);

  const handleClose = () => {
    navigate('/bergingsbassin');
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
      ...bergingsbassinEntity,
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
          ...bergingsbassinEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.bergingsbassin.home.createOrEditLabel" data-cy="BergingsbassinCreateUpdateHeading">
            Create or edit a Bergingsbassin
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
                <ValidatedField name="id" required readOnly id="bergingsbassin-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Bergendvermogen"
                id="bergingsbassin-bergendvermogen"
                name="bergendvermogen"
                data-cy="bergendvermogen"
                type="text"
              />
              <ValidatedField
                label="Pompledigingsvoorziening"
                id="bergingsbassin-pompledigingsvoorziening"
                name="pompledigingsvoorziening"
                data-cy="pompledigingsvoorziening"
                type="text"
              />
              <ValidatedField
                label="Pompspoelvoorziening"
                id="bergingsbassin-pompspoelvoorziening"
                name="pompspoelvoorziening"
                data-cy="pompspoelvoorziening"
                type="text"
              />
              <ValidatedField
                label="Spoelleiding"
                id="bergingsbassin-spoelleiding"
                name="spoelleiding"
                data-cy="spoelleiding"
                type="text"
              />
              <ValidatedField label="Vorm" id="bergingsbassin-vorm" name="vorm" data-cy="vorm" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bergingsbassin" replace color="info">
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

export default BergingsbassinUpdate;
