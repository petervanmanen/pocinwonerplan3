import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOverstortconstructie } from 'app/shared/model/overstortconstructie.model';
import { getEntity, updateEntity, createEntity, reset } from './overstortconstructie.reducer';

export const OverstortconstructieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const overstortconstructieEntity = useAppSelector(state => state.overstortconstructie.entity);
  const loading = useAppSelector(state => state.overstortconstructie.loading);
  const updating = useAppSelector(state => state.overstortconstructie.updating);
  const updateSuccess = useAppSelector(state => state.overstortconstructie.updateSuccess);

  const handleClose = () => {
    navigate('/overstortconstructie');
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
      ...overstortconstructieEntity,
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
          ...overstortconstructieEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.overstortconstructie.home.createOrEditLabel" data-cy="OverstortconstructieCreateUpdateHeading">
            Create or edit a Overstortconstructie
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
                <ValidatedField name="id" required readOnly id="overstortconstructie-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Bassin" id="overstortconstructie-bassin" name="bassin" data-cy="bassin" type="text" />
              <ValidatedField
                label="Drempelbreedte"
                id="overstortconstructie-drempelbreedte"
                name="drempelbreedte"
                data-cy="drempelbreedte"
                type="text"
              />
              <ValidatedField
                label="Drempelniveau"
                id="overstortconstructie-drempelniveau"
                name="drempelniveau"
                data-cy="drempelniveau"
                type="text"
              />
              <ValidatedField label="Klep" id="overstortconstructie-klep" name="klep" data-cy="klep" check type="checkbox" />
              <ValidatedField label="Type" id="overstortconstructie-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                label="Vormdrempel"
                id="overstortconstructie-vormdrempel"
                name="vormdrempel"
                data-cy="vormdrempel"
                type="text"
              />
              <ValidatedField label="Waking" id="overstortconstructie-waking" name="waking" data-cy="waking" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/overstortconstructie" replace color="info">
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

export default OverstortconstructieUpdate;
