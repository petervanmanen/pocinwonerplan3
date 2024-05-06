import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWegdeel } from 'app/shared/model/wegdeel.model';
import { getEntities as getWegdeels } from 'app/entities/wegdeel/wegdeel.reducer';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { getEntities as getMedewerkers } from 'app/entities/medewerker/medewerker.reducer';
import { IStremming } from 'app/shared/model/stremming.model';
import { getEntity, updateEntity, createEntity, reset } from './stremming.reducer';

export const StremmingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const wegdeels = useAppSelector(state => state.wegdeel.entities);
  const medewerkers = useAppSelector(state => state.medewerker.entities);
  const stremmingEntity = useAppSelector(state => state.stremming.entity);
  const loading = useAppSelector(state => state.stremming.loading);
  const updating = useAppSelector(state => state.stremming.updating);
  const updateSuccess = useAppSelector(state => state.stremming.updateSuccess);

  const handleClose = () => {
    navigate('/stremming');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWegdeels({}));
    dispatch(getMedewerkers({}));
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
      ...stremmingEntity,
      ...values,
      betreftWegdeels: mapIdList(values.betreftWegdeels),
      ingevoerddoorMedewerker: medewerkers.find(it => it.id.toString() === values.ingevoerddoorMedewerker?.toString()),
      gewijzigddoorMedewerker: medewerkers.find(it => it.id.toString() === values.gewijzigddoorMedewerker?.toString()),
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
          ...stremmingEntity,
          betreftWegdeels: stremmingEntity?.betreftWegdeels?.map(e => e.id.toString()),
          ingevoerddoorMedewerker: stremmingEntity?.ingevoerddoorMedewerker?.id,
          gewijzigddoorMedewerker: stremmingEntity?.gewijzigddoorMedewerker?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.stremming.home.createOrEditLabel" data-cy="StremmingCreateUpdateHeading">
            Create or edit a Stremming
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="stremming-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aantalgehinderden"
                id="stremming-aantalgehinderden"
                name="aantalgehinderden"
                data-cy="aantalgehinderden"
                type="text"
              />
              <ValidatedField
                label="Datumaanmelding"
                id="stremming-datumaanmelding"
                name="datumaanmelding"
                data-cy="datumaanmelding"
                type="text"
              />
              <ValidatedField label="Datumeinde" id="stremming-datumeinde" name="datumeinde" data-cy="datumeinde" type="text" />
              <ValidatedField label="Datumstart" id="stremming-datumstart" name="datumstart" data-cy="datumstart" type="text" />
              <ValidatedField
                label="Datumwijziging"
                id="stremming-datumwijziging"
                name="datumwijziging"
                data-cy="datumwijziging"
                type="text"
              />
              <ValidatedField
                label="Delentoegestaan"
                id="stremming-delentoegestaan"
                name="delentoegestaan"
                data-cy="delentoegestaan"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Geschiktvoorpublicatie"
                id="stremming-geschiktvoorpublicatie"
                name="geschiktvoorpublicatie"
                data-cy="geschiktvoorpublicatie"
                check
                type="checkbox"
              />
              <ValidatedField label="Hinderklasse" id="stremming-hinderklasse" name="hinderklasse" data-cy="hinderklasse" type="text" />
              <ValidatedField label="Locatie" id="stremming-locatie" name="locatie" data-cy="locatie" type="text" />
              <ValidatedField label="Naam" id="stremming-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Status" id="stremming-status" name="status" data-cy="status" type="text" />
              <ValidatedField
                label="Betreft Wegdeel"
                id="stremming-betreftWegdeel"
                data-cy="betreftWegdeel"
                type="select"
                multiple
                name="betreftWegdeels"
              >
                <option value="" key="0" />
                {wegdeels
                  ? wegdeels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="stremming-ingevoerddoorMedewerker"
                name="ingevoerddoorMedewerker"
                data-cy="ingevoerddoorMedewerker"
                label="Ingevoerddoor Medewerker"
                type="select"
              >
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
                id="stremming-gewijzigddoorMedewerker"
                name="gewijzigddoorMedewerker"
                data-cy="gewijzigddoorMedewerker"
                label="Gewijzigddoor Medewerker"
                type="select"
              >
                <option value="" key="0" />
                {medewerkers
                  ? medewerkers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/stremming" replace color="info">
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

export default StremmingUpdate;
