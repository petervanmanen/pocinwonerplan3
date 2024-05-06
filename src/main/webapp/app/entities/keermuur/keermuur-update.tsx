import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKeermuur } from 'app/shared/model/keermuur.model';
import { getEntity, updateEntity, createEntity, reset } from './keermuur.reducer';

export const KeermuurUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const keermuurEntity = useAppSelector(state => state.keermuur.entity);
  const loading = useAppSelector(state => state.keermuur.loading);
  const updating = useAppSelector(state => state.keermuur.updating);
  const updateSuccess = useAppSelector(state => state.keermuur.updateSuccess);

  const handleClose = () => {
    navigate('/keermuur');
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
      ...keermuurEntity,
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
          ...keermuurEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.keermuur.home.createOrEditLabel" data-cy="KeermuurCreateUpdateHeading">
            Create or edit a Keermuur
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="keermuur-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Belastingklassenieuw"
                id="keermuur-belastingklassenieuw"
                name="belastingklassenieuw"
                data-cy="belastingklassenieuw"
                type="text"
              />
              <ValidatedField
                label="Belastingklasseoud"
                id="keermuur-belastingklasseoud"
                name="belastingklasseoud"
                data-cy="belastingklasseoud"
                type="text"
              />
              <ValidatedField label="Type" id="keermuur-type" name="type" data-cy="type" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/keermuur" replace color="info">
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

export default KeermuurUpdate;
