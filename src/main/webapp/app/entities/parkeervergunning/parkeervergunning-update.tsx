import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IParkeerrecht } from 'app/shared/model/parkeerrecht.model';
import { getEntities as getParkeerrechts } from 'app/entities/parkeerrecht/parkeerrecht.reducer';
import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';
import { getEntities as getRechtspersoons } from 'app/entities/rechtspersoon/rechtspersoon.reducer';
import { IProductgroep } from 'app/shared/model/productgroep.model';
import { getEntities as getProductgroeps } from 'app/entities/productgroep/productgroep.reducer';
import { IProductsoort } from 'app/shared/model/productsoort.model';
import { getEntities as getProductsoorts } from 'app/entities/productsoort/productsoort.reducer';
import { IParkeervergunning } from 'app/shared/model/parkeervergunning.model';
import { getEntity, updateEntity, createEntity, reset } from './parkeervergunning.reducer';

export const ParkeervergunningUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const parkeerrechts = useAppSelector(state => state.parkeerrecht.entities);
  const rechtspersoons = useAppSelector(state => state.rechtspersoon.entities);
  const productgroeps = useAppSelector(state => state.productgroep.entities);
  const productsoorts = useAppSelector(state => state.productsoort.entities);
  const parkeervergunningEntity = useAppSelector(state => state.parkeervergunning.entity);
  const loading = useAppSelector(state => state.parkeervergunning.loading);
  const updating = useAppSelector(state => state.parkeervergunning.updating);
  const updateSuccess = useAppSelector(state => state.parkeervergunning.updateSuccess);

  const handleClose = () => {
    navigate('/parkeervergunning');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getParkeerrechts({}));
    dispatch(getRechtspersoons({}));
    dispatch(getProductgroeps({}));
    dispatch(getProductsoorts({}));
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
      ...parkeervergunningEntity,
      ...values,
      resulteertParkeerrecht: parkeerrechts.find(it => it.id.toString() === values.resulteertParkeerrecht?.toString()),
      houderRechtspersoon: rechtspersoons.find(it => it.id.toString() === values.houderRechtspersoon?.toString()),
      soortProductgroep: productgroeps.find(it => it.id.toString() === values.soortProductgroep?.toString()),
      soortProductsoort: productsoorts.find(it => it.id.toString() === values.soortProductsoort?.toString()),
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
          ...parkeervergunningEntity,
          resulteertParkeerrecht: parkeervergunningEntity?.resulteertParkeerrecht?.id,
          houderRechtspersoon: parkeervergunningEntity?.houderRechtspersoon?.id,
          soortProductgroep: parkeervergunningEntity?.soortProductgroep?.id,
          soortProductsoort: parkeervergunningEntity?.soortProductsoort?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.parkeervergunning.home.createOrEditLabel" data-cy="ParkeervergunningCreateUpdateHeading">
            Create or edit a Parkeervergunning
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
                <ValidatedField name="id" required readOnly id="parkeervergunning-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumeindegeldigheid"
                id="parkeervergunning-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="text"
              />
              <ValidatedField
                label="Datumreservering"
                id="parkeervergunning-datumreservering"
                name="datumreservering"
                data-cy="datumreservering"
                type="date"
              />
              <ValidatedField label="Datumstart" id="parkeervergunning-datumstart" name="datumstart" data-cy="datumstart" type="text" />
              <ValidatedField label="Kenteken" id="parkeervergunning-kenteken" name="kenteken" data-cy="kenteken" type="text" />
              <ValidatedField
                label="Minutenafgeschreven"
                id="parkeervergunning-minutenafgeschreven"
                name="minutenafgeschreven"
                data-cy="minutenafgeschreven"
                type="text"
              />
              <ValidatedField
                label="Minutengeldig"
                id="parkeervergunning-minutengeldig"
                name="minutengeldig"
                data-cy="minutengeldig"
                type="text"
              />
              <ValidatedField
                label="Minutenresterend"
                id="parkeervergunning-minutenresterend"
                name="minutenresterend"
                data-cy="minutenresterend"
                type="text"
              />
              <ValidatedField label="Nummer" id="parkeervergunning-nummer" name="nummer" data-cy="nummer" type="text" />
              <ValidatedField label="Type" id="parkeervergunning-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                id="parkeervergunning-resulteertParkeerrecht"
                name="resulteertParkeerrecht"
                data-cy="resulteertParkeerrecht"
                label="Resulteert Parkeerrecht"
                type="select"
              >
                <option value="" key="0" />
                {parkeerrechts
                  ? parkeerrechts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="parkeervergunning-houderRechtspersoon"
                name="houderRechtspersoon"
                data-cy="houderRechtspersoon"
                label="Houder Rechtspersoon"
                type="select"
              >
                <option value="" key="0" />
                {rechtspersoons
                  ? rechtspersoons.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="parkeervergunning-soortProductgroep"
                name="soortProductgroep"
                data-cy="soortProductgroep"
                label="Soort Productgroep"
                type="select"
              >
                <option value="" key="0" />
                {productgroeps
                  ? productgroeps.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="parkeervergunning-soortProductsoort"
                name="soortProductsoort"
                data-cy="soortProductsoort"
                label="Soort Productsoort"
                type="select"
              >
                <option value="" key="0" />
                {productsoorts
                  ? productsoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/parkeervergunning" replace color="info">
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

export default ParkeervergunningUpdate;
