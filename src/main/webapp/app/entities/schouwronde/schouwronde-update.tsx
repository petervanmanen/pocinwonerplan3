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
import { IAreaal } from 'app/shared/model/areaal.model';
import { getEntities as getAreaals } from 'app/entities/areaal/areaal.reducer';
import { ISchouwronde } from 'app/shared/model/schouwronde.model';
import { getEntity, updateEntity, createEntity, reset } from './schouwronde.reducer';

export const SchouwrondeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const medewerkers = useAppSelector(state => state.medewerker.entities);
  const areaals = useAppSelector(state => state.areaal.entities);
  const schouwrondeEntity = useAppSelector(state => state.schouwronde.entity);
  const loading = useAppSelector(state => state.schouwronde.loading);
  const updating = useAppSelector(state => state.schouwronde.updating);
  const updateSuccess = useAppSelector(state => state.schouwronde.updateSuccess);

  const handleClose = () => {
    navigate('/schouwronde');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMedewerkers({}));
    dispatch(getAreaals({}));
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
      ...schouwrondeEntity,
      ...values,
      voertuitMedewerker: medewerkers.find(it => it.id.toString() === values.voertuitMedewerker?.toString()),
      binnenAreaals: mapIdList(values.binnenAreaals),
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
          ...schouwrondeEntity,
          voertuitMedewerker: schouwrondeEntity?.voertuitMedewerker?.id,
          binnenAreaals: schouwrondeEntity?.binnenAreaals?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.schouwronde.home.createOrEditLabel" data-cy="SchouwrondeCreateUpdateHeading">
            Create or edit a Schouwronde
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="schouwronde-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                id="schouwronde-voertuitMedewerker"
                name="voertuitMedewerker"
                data-cy="voertuitMedewerker"
                label="Voertuit Medewerker"
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
                label="Binnen Areaal"
                id="schouwronde-binnenAreaal"
                data-cy="binnenAreaal"
                type="select"
                multiple
                name="binnenAreaals"
              >
                <option value="" key="0" />
                {areaals
                  ? areaals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/schouwronde" replace color="info">
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

export default SchouwrondeUpdate;
