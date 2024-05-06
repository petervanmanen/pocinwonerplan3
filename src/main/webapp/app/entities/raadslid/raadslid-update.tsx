import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRaadscommissie } from 'app/shared/model/raadscommissie.model';
import { getEntities as getRaadscommissies } from 'app/entities/raadscommissie/raadscommissie.reducer';
import { IRaadslid } from 'app/shared/model/raadslid.model';
import { getEntity, updateEntity, createEntity, reset } from './raadslid.reducer';

export const RaadslidUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const raadscommissies = useAppSelector(state => state.raadscommissie.entities);
  const raadslidEntity = useAppSelector(state => state.raadslid.entity);
  const loading = useAppSelector(state => state.raadslid.loading);
  const updating = useAppSelector(state => state.raadslid.updating);
  const updateSuccess = useAppSelector(state => state.raadslid.updateSuccess);

  const handleClose = () => {
    navigate('/raadslid');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRaadscommissies({}));
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
      ...raadslidEntity,
      ...values,
      islidvanRaadscommissies: mapIdList(values.islidvanRaadscommissies),
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
          ...raadslidEntity,
          islidvanRaadscommissies: raadslidEntity?.islidvanRaadscommissies?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.raadslid.home.createOrEditLabel" data-cy="RaadslidCreateUpdateHeading">
            Create or edit a Raadslid
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="raadslid-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Achternaam" id="raadslid-achternaam" name="achternaam" data-cy="achternaam" type="text" />
              <ValidatedField
                label="Datumaanstelling"
                id="raadslid-datumaanstelling"
                name="datumaanstelling"
                data-cy="datumaanstelling"
                type="date"
              />
              <ValidatedField
                label="Datumuittreding"
                id="raadslid-datumuittreding"
                name="datumuittreding"
                data-cy="datumuittreding"
                type="date"
              />
              <ValidatedField label="Fractie" id="raadslid-fractie" name="fractie" data-cy="fractie" type="text" />
              <ValidatedField label="Titel" id="raadslid-titel" name="titel" data-cy="titel" type="text" />
              <ValidatedField label="Voornaam" id="raadslid-voornaam" name="voornaam" data-cy="voornaam" type="text" />
              <ValidatedField
                label="Islidvan Raadscommissie"
                id="raadslid-islidvanRaadscommissie"
                data-cy="islidvanRaadscommissie"
                type="select"
                multiple
                name="islidvanRaadscommissies"
              >
                <option value="" key="0" />
                {raadscommissies
                  ? raadscommissies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/raadslid" replace color="info">
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

export default RaadslidUpdate;
