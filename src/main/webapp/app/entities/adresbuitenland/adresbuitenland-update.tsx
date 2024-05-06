import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAdresbuitenland } from 'app/shared/model/adresbuitenland.model';
import { getEntity, updateEntity, createEntity, reset } from './adresbuitenland.reducer';

export const AdresbuitenlandUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const adresbuitenlandEntity = useAppSelector(state => state.adresbuitenland.entity);
  const loading = useAppSelector(state => state.adresbuitenland.loading);
  const updating = useAppSelector(state => state.adresbuitenland.updating);
  const updateSuccess = useAppSelector(state => state.adresbuitenland.updateSuccess);

  const handleClose = () => {
    navigate('/adresbuitenland');
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
      ...adresbuitenlandEntity,
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
          ...adresbuitenlandEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.adresbuitenland.home.createOrEditLabel" data-cy="AdresbuitenlandCreateUpdateHeading">
            Create or edit a Adresbuitenland
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
                <ValidatedField name="id" required readOnly id="adresbuitenland-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Adresregelbuitenland 1"
                id="adresbuitenland-adresregelbuitenland1"
                name="adresregelbuitenland1"
                data-cy="adresregelbuitenland1"
                type="text"
              />
              <ValidatedField
                label="Adresregelbuitenland 2"
                id="adresbuitenland-adresregelbuitenland2"
                name="adresregelbuitenland2"
                data-cy="adresregelbuitenland2"
                type="text"
              />
              <ValidatedField
                label="Adresregelbuitenland 3"
                id="adresbuitenland-adresregelbuitenland3"
                name="adresregelbuitenland3"
                data-cy="adresregelbuitenland3"
                type="text"
              />
              <ValidatedField
                label="Datumaanvangadresbuitenland"
                id="adresbuitenland-datumaanvangadresbuitenland"
                name="datumaanvangadresbuitenland"
                data-cy="datumaanvangadresbuitenland"
                type="date"
              />
              <ValidatedField
                label="Datuminschrijvinggemeente"
                id="adresbuitenland-datuminschrijvinggemeente"
                name="datuminschrijvinggemeente"
                data-cy="datuminschrijvinggemeente"
                type="date"
              />
              <ValidatedField
                label="Datumvestigingnederland"
                id="adresbuitenland-datumvestigingnederland"
                name="datumvestigingnederland"
                data-cy="datumvestigingnederland"
                type="date"
              />
              <ValidatedField
                label="Gemeentevaninschrijving"
                id="adresbuitenland-gemeentevaninschrijving"
                name="gemeentevaninschrijving"
                data-cy="gemeentevaninschrijving"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Landadresbuitenland"
                id="adresbuitenland-landadresbuitenland"
                name="landadresbuitenland"
                data-cy="landadresbuitenland"
                type="text"
              />
              <ValidatedField
                label="Landwaarvandaaningeschreven"
                id="adresbuitenland-landwaarvandaaningeschreven"
                name="landwaarvandaaningeschreven"
                data-cy="landwaarvandaaningeschreven"
                type="text"
              />
              <ValidatedField
                label="Omschrijvingvandeaangifteadreshouding"
                id="adresbuitenland-omschrijvingvandeaangifteadreshouding"
                name="omschrijvingvandeaangifteadreshouding"
                data-cy="omschrijvingvandeaangifteadreshouding"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/adresbuitenland" replace color="info">
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

export default AdresbuitenlandUpdate;
