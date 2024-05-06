import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBruikleen } from 'app/shared/model/bruikleen.model';
import { getEntities as getBruikleens } from 'app/entities/bruikleen/bruikleen.reducer';
import { IStandplaats } from 'app/shared/model/standplaats.model';
import { getEntities as getStandplaats } from 'app/entities/standplaats/standplaats.reducer';
import { IBelanghebbende } from 'app/shared/model/belanghebbende.model';
import { getEntities as getBelanghebbendes } from 'app/entities/belanghebbende/belanghebbende.reducer';
import { ITentoonstelling } from 'app/shared/model/tentoonstelling.model';
import { getEntities as getTentoonstellings } from 'app/entities/tentoonstelling/tentoonstelling.reducer';
import { ICollectie } from 'app/shared/model/collectie.model';
import { getEntities as getCollecties } from 'app/entities/collectie/collectie.reducer';
import { IIncident } from 'app/shared/model/incident.model';
import { getEntities as getIncidents } from 'app/entities/incident/incident.reducer';
import { IMuseumobject } from 'app/shared/model/museumobject.model';
import { getEntity, updateEntity, createEntity, reset } from './museumobject.reducer';

export const MuseumobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bruikleens = useAppSelector(state => state.bruikleen.entities);
  const standplaats = useAppSelector(state => state.standplaats.entities);
  const belanghebbendes = useAppSelector(state => state.belanghebbende.entities);
  const tentoonstellings = useAppSelector(state => state.tentoonstelling.entities);
  const collecties = useAppSelector(state => state.collectie.entities);
  const incidents = useAppSelector(state => state.incident.entities);
  const museumobjectEntity = useAppSelector(state => state.museumobject.entity);
  const loading = useAppSelector(state => state.museumobject.loading);
  const updating = useAppSelector(state => state.museumobject.updating);
  const updateSuccess = useAppSelector(state => state.museumobject.updateSuccess);

  const handleClose = () => {
    navigate('/museumobject');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBruikleens({}));
    dispatch(getStandplaats({}));
    dispatch(getBelanghebbendes({}));
    dispatch(getTentoonstellings({}));
    dispatch(getCollecties({}));
    dispatch(getIncidents({}));
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
      ...museumobjectEntity,
      ...values,
      betreftBruikleen: bruikleens.find(it => it.id.toString() === values.betreftBruikleen?.toString()),
      locatieStandplaats: standplaats.find(it => it.id.toString() === values.locatieStandplaats?.toString()),
      heeftBelanghebbendes: mapIdList(values.heeftBelanghebbendes),
      onderdeelTentoonstellings: mapIdList(values.onderdeelTentoonstellings),
      bevatCollecties: mapIdList(values.bevatCollecties),
      betreftIncidents: mapIdList(values.betreftIncidents),
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
          ...museumobjectEntity,
          betreftBruikleen: museumobjectEntity?.betreftBruikleen?.id,
          locatieStandplaats: museumobjectEntity?.locatieStandplaats?.id,
          heeftBelanghebbendes: museumobjectEntity?.heeftBelanghebbendes?.map(e => e.id.toString()),
          onderdeelTentoonstellings: museumobjectEntity?.onderdeelTentoonstellings?.map(e => e.id.toString()),
          bevatCollecties: museumobjectEntity?.bevatCollecties?.map(e => e.id.toString()),
          betreftIncidents: museumobjectEntity?.betreftIncidents?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.museumobject.home.createOrEditLabel" data-cy="MuseumobjectCreateUpdateHeading">
            Create or edit a Museumobject
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="museumobject-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Afmeting" id="museumobject-afmeting" name="afmeting" data-cy="afmeting" type="text" />
              <ValidatedField label="Bezittot" id="museumobject-bezittot" name="bezittot" data-cy="bezittot" type="date" />
              <ValidatedField label="Bezitvanaf" id="museumobject-bezitvanaf" name="bezitvanaf" data-cy="bezitvanaf" type="date" />
              <ValidatedField label="Medium" id="museumobject-medium" name="medium" data-cy="medium" type="text" />
              <ValidatedField label="Verkrijging" id="museumobject-verkrijging" name="verkrijging" data-cy="verkrijging" type="text" />
              <ValidatedField
                id="museumobject-betreftBruikleen"
                name="betreftBruikleen"
                data-cy="betreftBruikleen"
                label="Betreft Bruikleen"
                type="select"
              >
                <option value="" key="0" />
                {bruikleens
                  ? bruikleens.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="museumobject-locatieStandplaats"
                name="locatieStandplaats"
                data-cy="locatieStandplaats"
                label="Locatie Standplaats"
                type="select"
              >
                <option value="" key="0" />
                {standplaats
                  ? standplaats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeft Belanghebbende"
                id="museumobject-heeftBelanghebbende"
                data-cy="heeftBelanghebbende"
                type="select"
                multiple
                name="heeftBelanghebbendes"
              >
                <option value="" key="0" />
                {belanghebbendes
                  ? belanghebbendes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Onderdeel Tentoonstelling"
                id="museumobject-onderdeelTentoonstelling"
                data-cy="onderdeelTentoonstelling"
                type="select"
                multiple
                name="onderdeelTentoonstellings"
              >
                <option value="" key="0" />
                {tentoonstellings
                  ? tentoonstellings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Bevat Collectie"
                id="museumobject-bevatCollectie"
                data-cy="bevatCollectie"
                type="select"
                multiple
                name="bevatCollecties"
              >
                <option value="" key="0" />
                {collecties
                  ? collecties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Betreft Incident"
                id="museumobject-betreftIncident"
                data-cy="betreftIncident"
                type="select"
                multiple
                name="betreftIncidents"
              >
                <option value="" key="0" />
                {incidents
                  ? incidents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/museumobject" replace color="info">
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

export default MuseumobjectUpdate;
