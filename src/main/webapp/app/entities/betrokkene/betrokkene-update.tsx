import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMedewerker } from 'app/shared/model/medewerker.model';
import { getEntities as getMedewerkers } from 'app/entities/medewerker/medewerker.reducer';
import { IZaak } from 'app/shared/model/zaak.model';
import { getEntities as getZaaks } from 'app/entities/zaak/zaak.reducer';
import { IBetrokkene } from 'app/shared/model/betrokkene.model';
import { getEntity, updateEntity, createEntity, reset } from './betrokkene.reducer';

export const BetrokkeneUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const medewerkers = useAppSelector(state => state.medewerker.entities);
  const zaaks = useAppSelector(state => state.zaak.entities);
  const betrokkeneEntity = useAppSelector(state => state.betrokkene.entity);
  const loading = useAppSelector(state => state.betrokkene.loading);
  const updating = useAppSelector(state => state.betrokkene.updating);
  const updateSuccess = useAppSelector(state => state.betrokkene.updateSuccess);

  const handleClose = () => {
    navigate('/betrokkene');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMedewerkers({}));
    dispatch(getZaaks({}));
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
      ...betrokkeneEntity,
      ...values,
      isMedewerker: medewerkers.find(it => it.id.toString() === values.isMedewerker?.toString()),
      oefentuitZaaks: mapIdList(values.oefentuitZaaks),
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
          ...betrokkeneEntity,
          isMedewerker: betrokkeneEntity?.isMedewerker?.id,
          oefentuitZaaks: betrokkeneEntity?.oefentuitZaaks?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.betrokkene.home.createOrEditLabel" data-cy="BetrokkeneCreateUpdateHeading">
            Create or edit a Betrokkene
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="betrokkene-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Adresbinnenland"
                id="betrokkene-adresbinnenland"
                name="adresbinnenland"
                data-cy="adresbinnenland"
                type="text"
              />
              <ValidatedField
                label="Adresbuitenland"
                id="betrokkene-adresbuitenland"
                name="adresbuitenland"
                data-cy="adresbuitenland"
                type="text"
              />
              <ValidatedField
                label="Identificatie"
                id="betrokkene-identificatie"
                name="identificatie"
                data-cy="identificatie"
                type="text"
              />
              <ValidatedField label="Naam" id="betrokkene-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Rol" id="betrokkene-rol" name="rol" data-cy="rol" type="text" />
              <ValidatedField id="betrokkene-isMedewerker" name="isMedewerker" data-cy="isMedewerker" label="Is Medewerker" type="select">
                <option value="" key="0" />
                {medewerkers
                  ? medewerkers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Oefentuit Zaak"
                id="betrokkene-oefentuitZaak"
                data-cy="oefentuitZaak"
                type="select"
                multiple
                name="oefentuitZaaks"
              >
                <option value="" key="0" />
                {zaaks
                  ? zaaks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/betrokkene" replace color="info">
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

export default BetrokkeneUpdate;
