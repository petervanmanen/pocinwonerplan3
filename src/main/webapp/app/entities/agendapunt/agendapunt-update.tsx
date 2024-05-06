import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVergadering } from 'app/shared/model/vergadering.model';
import { getEntities as getVergaderings } from 'app/entities/vergadering/vergadering.reducer';
import { IRaadsstuk } from 'app/shared/model/raadsstuk.model';
import { getEntities as getRaadsstuks } from 'app/entities/raadsstuk/raadsstuk.reducer';
import { IAgendapunt } from 'app/shared/model/agendapunt.model';
import { getEntity, updateEntity, createEntity, reset } from './agendapunt.reducer';

export const AgendapuntUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vergaderings = useAppSelector(state => state.vergadering.entities);
  const raadsstuks = useAppSelector(state => state.raadsstuk.entities);
  const agendapuntEntity = useAppSelector(state => state.agendapunt.entity);
  const loading = useAppSelector(state => state.agendapunt.loading);
  const updating = useAppSelector(state => state.agendapunt.updating);
  const updateSuccess = useAppSelector(state => state.agendapunt.updateSuccess);

  const handleClose = () => {
    navigate('/agendapunt');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVergaderings({}));
    dispatch(getRaadsstuks({}));
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
      ...agendapuntEntity,
      ...values,
      heeftVergadering: vergaderings.find(it => it.id.toString() === values.heeftVergadering?.toString()),
      behandeltRaadsstuks: mapIdList(values.behandeltRaadsstuks),
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
          ...agendapuntEntity,
          heeftVergadering: agendapuntEntity?.heeftVergadering?.id,
          behandeltRaadsstuks: agendapuntEntity?.behandeltRaadsstuks?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.agendapunt.home.createOrEditLabel" data-cy="AgendapuntCreateUpdateHeading">
            Create or edit a Agendapunt
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="agendapunt-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Nummer" id="agendapunt-nummer" name="nummer" data-cy="nummer" type="text" />
              <ValidatedField label="Omschrijving" id="agendapunt-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Titel" id="agendapunt-titel" name="titel" data-cy="titel" type="text" />
              <ValidatedField
                id="agendapunt-heeftVergadering"
                name="heeftVergadering"
                data-cy="heeftVergadering"
                label="Heeft Vergadering"
                type="select"
              >
                <option value="" key="0" />
                {vergaderings
                  ? vergaderings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Behandelt Raadsstuk"
                id="agendapunt-behandeltRaadsstuk"
                data-cy="behandeltRaadsstuk"
                type="select"
                multiple
                name="behandeltRaadsstuks"
              >
                <option value="" key="0" />
                {raadsstuks
                  ? raadsstuks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/agendapunt" replace color="info">
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

export default AgendapuntUpdate;
