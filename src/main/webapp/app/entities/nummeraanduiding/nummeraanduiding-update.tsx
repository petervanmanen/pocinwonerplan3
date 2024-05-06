import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWoonplaats } from 'app/shared/model/woonplaats.model';
import { getEntities as getWoonplaats } from 'app/entities/woonplaats/woonplaats.reducer';
import { IBuurt } from 'app/shared/model/buurt.model';
import { getEntities as getBuurts } from 'app/entities/buurt/buurt.reducer';
import { IGebied } from 'app/shared/model/gebied.model';
import { getEntities as getGebieds } from 'app/entities/gebied/gebied.reducer';
import { INummeraanduiding } from 'app/shared/model/nummeraanduiding.model';
import { getEntity, updateEntity, createEntity, reset } from './nummeraanduiding.reducer';

export const NummeraanduidingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const woonplaats = useAppSelector(state => state.woonplaats.entities);
  const buurts = useAppSelector(state => state.buurt.entities);
  const gebieds = useAppSelector(state => state.gebied.entities);
  const nummeraanduidingEntity = useAppSelector(state => state.nummeraanduiding.entity);
  const loading = useAppSelector(state => state.nummeraanduiding.loading);
  const updating = useAppSelector(state => state.nummeraanduiding.updating);
  const updateSuccess = useAppSelector(state => state.nummeraanduiding.updateSuccess);

  const handleClose = () => {
    navigate('/nummeraanduiding');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWoonplaats({}));
    dispatch(getBuurts({}));
    dispatch(getGebieds({}));
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
      ...nummeraanduidingEntity,
      ...values,
      ligtinWoonplaats: woonplaats.find(it => it.id.toString() === values.ligtinWoonplaats?.toString()),
      ligtinBuurt: buurts.find(it => it.id.toString() === values.ligtinBuurt?.toString()),
      ligtinGebieds: mapIdList(values.ligtinGebieds),
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
          ...nummeraanduidingEntity,
          ligtinWoonplaats: nummeraanduidingEntity?.ligtinWoonplaats?.id,
          ligtinBuurt: nummeraanduidingEntity?.ligtinBuurt?.id,
          ligtinGebieds: nummeraanduidingEntity?.ligtinGebieds?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.nummeraanduiding.home.createOrEditLabel" data-cy="NummeraanduidingCreateUpdateHeading">
            Create or edit a Nummeraanduiding
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
                <ValidatedField name="id" required readOnly id="nummeraanduiding-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbegingeldigheid"
                id="nummeraanduiding-datumbegingeldigheid"
                name="datumbegingeldigheid"
                data-cy="datumbegingeldigheid"
                type="date"
              />
              <ValidatedField label="Datumeinde" id="nummeraanduiding-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="nummeraanduiding-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField label="Datumingang" id="nummeraanduiding-datumingang" name="datumingang" data-cy="datumingang" type="date" />
              <ValidatedField
                label="Geconstateerd"
                id="nummeraanduiding-geconstateerd"
                name="geconstateerd"
                data-cy="geconstateerd"
                check
                type="checkbox"
              />
              <ValidatedField label="Geometrie" id="nummeraanduiding-geometrie" name="geometrie" data-cy="geometrie" type="text" />
              <ValidatedField label="Huisletter" id="nummeraanduiding-huisletter" name="huisletter" data-cy="huisletter" type="text" />
              <ValidatedField label="Huisnummer" id="nummeraanduiding-huisnummer" name="huisnummer" data-cy="huisnummer" type="text" />
              <ValidatedField
                label="Huisnummertoevoeging"
                id="nummeraanduiding-huisnummertoevoeging"
                name="huisnummertoevoeging"
                data-cy="huisnummertoevoeging"
                type="text"
              />
              <ValidatedField
                label="Identificatie"
                id="nummeraanduiding-identificatie"
                name="identificatie"
                data-cy="identificatie"
                type="text"
              />
              <ValidatedField
                label="Inonderzoek"
                id="nummeraanduiding-inonderzoek"
                name="inonderzoek"
                data-cy="inonderzoek"
                check
                type="checkbox"
              />
              <ValidatedField label="Postcode" id="nummeraanduiding-postcode" name="postcode" data-cy="postcode" type="text" />
              <ValidatedField label="Status" id="nummeraanduiding-status" name="status" data-cy="status" type="text" />
              <ValidatedField
                label="Typeadresseerbaarobject"
                id="nummeraanduiding-typeadresseerbaarobject"
                name="typeadresseerbaarobject"
                data-cy="typeadresseerbaarobject"
                type="text"
              />
              <ValidatedField label="Versie" id="nummeraanduiding-versie" name="versie" data-cy="versie" type="text" />
              <ValidatedField
                id="nummeraanduiding-ligtinWoonplaats"
                name="ligtinWoonplaats"
                data-cy="ligtinWoonplaats"
                label="Ligtin Woonplaats"
                type="select"
              >
                <option value="" key="0" />
                {woonplaats
                  ? woonplaats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="nummeraanduiding-ligtinBuurt" name="ligtinBuurt" data-cy="ligtinBuurt" label="Ligtin Buurt" type="select">
                <option value="" key="0" />
                {buurts
                  ? buurts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Ligtin Gebied"
                id="nummeraanduiding-ligtinGebied"
                data-cy="ligtinGebied"
                type="select"
                multiple
                name="ligtinGebieds"
              >
                <option value="" key="0" />
                {gebieds
                  ? gebieds.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/nummeraanduiding" replace color="info">
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

export default NummeraanduidingUpdate;
