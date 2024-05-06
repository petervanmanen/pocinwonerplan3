import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILandofgebied } from 'app/shared/model/landofgebied.model';
import { getEntity, updateEntity, createEntity, reset } from './landofgebied.reducer';

export const LandofgebiedUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const landofgebiedEntity = useAppSelector(state => state.landofgebied.entity);
  const loading = useAppSelector(state => state.landofgebied.loading);
  const updating = useAppSelector(state => state.landofgebied.updating);
  const updateSuccess = useAppSelector(state => state.landofgebied.updateSuccess);

  const handleClose = () => {
    navigate('/landofgebied');
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
      ...landofgebiedEntity,
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
          ...landofgebiedEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.landofgebied.home.createOrEditLabel" data-cy="LandofgebiedCreateUpdateHeading">
            Create or edit a Landofgebied
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="landofgebied-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumeindeland"
                id="landofgebied-datumeindeland"
                name="datumeindeland"
                data-cy="datumeindeland"
                type="date"
              />
              <ValidatedField
                label="Datumingangland"
                id="landofgebied-datumingangland"
                name="datumingangland"
                data-cy="datumingangland"
                type="date"
              />
              <ValidatedField label="Landcode" id="landofgebied-landcode" name="landcode" data-cy="landcode" type="text" />
              <ValidatedField label="Landcodeiso" id="landofgebied-landcodeiso" name="landcodeiso" data-cy="landcodeiso" type="text" />
              <ValidatedField label="Landnaam" id="landofgebied-landnaam" name="landnaam" data-cy="landnaam" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/landofgebied" replace color="info">
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

export default LandofgebiedUpdate;
