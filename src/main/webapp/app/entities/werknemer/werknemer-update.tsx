import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGeweldsincident } from 'app/shared/model/geweldsincident.model';
import { getEntities as getGeweldsincidents } from 'app/entities/geweldsincident/geweldsincident.reducer';
import { IRol } from 'app/shared/model/rol.model';
import { getEntities as getRols } from 'app/entities/rol/rol.reducer';
import { ISollicitatiegesprek } from 'app/shared/model/sollicitatiegesprek.model';
import { getEntities as getSollicitatiegespreks } from 'app/entities/sollicitatiegesprek/sollicitatiegesprek.reducer';
import { IWerknemer } from 'app/shared/model/werknemer.model';
import { getEntity, updateEntity, createEntity, reset } from './werknemer.reducer';

export const WerknemerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const geweldsincidents = useAppSelector(state => state.geweldsincident.entities);
  const rols = useAppSelector(state => state.rol.entities);
  const sollicitatiegespreks = useAppSelector(state => state.sollicitatiegesprek.entities);
  const werknemerEntity = useAppSelector(state => state.werknemer.entity);
  const loading = useAppSelector(state => state.werknemer.loading);
  const updating = useAppSelector(state => state.werknemer.updating);
  const updateSuccess = useAppSelector(state => state.werknemer.updateSuccess);

  const handleClose = () => {
    navigate('/werknemer');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getGeweldsincidents({}));
    dispatch(getRols({}));
    dispatch(getSollicitatiegespreks({}));
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
      ...werknemerEntity,
      ...values,
      heeftondergaanGeweldsincident: geweldsincidents.find(it => it.id.toString() === values.heeftondergaanGeweldsincident?.toString()),
      heeftRols: mapIdList(values.heeftRols),
      doetsollicitatiegesprekSollicitatiegespreks: mapIdList(values.doetsollicitatiegesprekSollicitatiegespreks),
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
          ...werknemerEntity,
          heeftondergaanGeweldsincident: werknemerEntity?.heeftondergaanGeweldsincident?.id,
          heeftRols: werknemerEntity?.heeftRols?.map(e => e.id.toString()),
          doetsollicitatiegesprekSollicitatiegespreks: werknemerEntity?.doetsollicitatiegesprekSollicitatiegespreks?.map(e =>
            e.id.toString(),
          ),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.werknemer.home.createOrEditLabel" data-cy="WerknemerCreateUpdateHeading">
            Create or edit a Werknemer
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="werknemer-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Geboortedatum" id="werknemer-geboortedatum" name="geboortedatum" data-cy="geboortedatum" type="date" />
              <ValidatedField label="Naam" id="werknemer-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Voornaam" id="werknemer-voornaam" name="voornaam" data-cy="voornaam" type="text" />
              <ValidatedField label="Woonplaats" id="werknemer-woonplaats" name="woonplaats" data-cy="woonplaats" type="text" />
              <ValidatedField
                id="werknemer-heeftondergaanGeweldsincident"
                name="heeftondergaanGeweldsincident"
                data-cy="heeftondergaanGeweldsincident"
                label="Heeftondergaan Geweldsincident"
                type="select"
              >
                <option value="" key="0" />
                {geweldsincidents
                  ? geweldsincidents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField label="Heeft Rol" id="werknemer-heeftRol" data-cy="heeftRol" type="select" multiple name="heeftRols">
                <option value="" key="0" />
                {rols
                  ? rols.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Doetsollicitatiegesprek Sollicitatiegesprek"
                id="werknemer-doetsollicitatiegesprekSollicitatiegesprek"
                data-cy="doetsollicitatiegesprekSollicitatiegesprek"
                type="select"
                multiple
                name="doetsollicitatiegesprekSollicitatiegespreks"
              >
                <option value="" key="0" />
                {sollicitatiegespreks
                  ? sollicitatiegespreks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/werknemer" replace color="info">
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

export default WerknemerUpdate;
