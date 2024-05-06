import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInstallatie } from 'app/shared/model/installatie.model';
import { getEntity, updateEntity, createEntity, reset } from './installatie.reducer';

export const InstallatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const installatieEntity = useAppSelector(state => state.installatie.entity);
  const loading = useAppSelector(state => state.installatie.loading);
  const updating = useAppSelector(state => state.installatie.updating);
  const updateSuccess = useAppSelector(state => state.installatie.updateSuccess);

  const handleClose = () => {
    navigate('/installatie');
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
      ...installatieEntity,
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
          ...installatieEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.installatie.home.createOrEditLabel" data-cy="InstallatieCreateUpdateHeading">
            Create or edit a Installatie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="installatie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Breedte" id="installatie-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField label="Eancode" id="installatie-eancode" name="eancode" data-cy="eancode" type="text" />
              <ValidatedField label="Fabrikant" id="installatie-fabrikant" name="fabrikant" data-cy="fabrikant" type="text" />
              <ValidatedField label="Hoogte" id="installatie-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField
                label="Inbelgegevens"
                id="installatie-inbelgegevens"
                name="inbelgegevens"
                data-cy="inbelgegevens"
                type="text"
              />
              <ValidatedField label="Installateur" id="installatie-installateur" name="installateur" data-cy="installateur" type="text" />
              <ValidatedField
                label="Jaaronderhouduitgevoerd"
                id="installatie-jaaronderhouduitgevoerd"
                name="jaaronderhouduitgevoerd"
                data-cy="jaaronderhouduitgevoerd"
                type="text"
              />
              <ValidatedField label="Lengte" id="installatie-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField label="Leverancier" id="installatie-leverancier" name="leverancier" data-cy="leverancier" type="text" />
              <ValidatedField
                label="Typecommunicatie"
                id="installatie-typecommunicatie"
                name="typecommunicatie"
                data-cy="typecommunicatie"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/installatie" replace color="info">
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

export default InstallatieUpdate;
