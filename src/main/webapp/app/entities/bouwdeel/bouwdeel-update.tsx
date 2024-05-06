import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVastgoedobject } from 'app/shared/model/vastgoedobject.model';
import { getEntities as getVastgoedobjects } from 'app/entities/vastgoedobject/vastgoedobject.reducer';
import { IWerkbon } from 'app/shared/model/werkbon.model';
import { getEntities as getWerkbons } from 'app/entities/werkbon/werkbon.reducer';
import { IBouwdeel } from 'app/shared/model/bouwdeel.model';
import { getEntity, updateEntity, createEntity, reset } from './bouwdeel.reducer';

export const BouwdeelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vastgoedobjects = useAppSelector(state => state.vastgoedobject.entities);
  const werkbons = useAppSelector(state => state.werkbon.entities);
  const bouwdeelEntity = useAppSelector(state => state.bouwdeel.entity);
  const loading = useAppSelector(state => state.bouwdeel.loading);
  const updating = useAppSelector(state => state.bouwdeel.updating);
  const updateSuccess = useAppSelector(state => state.bouwdeel.updateSuccess);

  const handleClose = () => {
    navigate('/bouwdeel');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVastgoedobjects({}));
    dispatch(getWerkbons({}));
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
      ...bouwdeelEntity,
      ...values,
      bestaatuitVastgoedobject: vastgoedobjects.find(it => it.id.toString() === values.bestaatuitVastgoedobject?.toString()),
      betreftWerkbons: mapIdList(values.betreftWerkbons),
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
          ...bouwdeelEntity,
          bestaatuitVastgoedobject: bouwdeelEntity?.bestaatuitVastgoedobject?.id,
          betreftWerkbons: bouwdeelEntity?.betreftWerkbons?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.bouwdeel.home.createOrEditLabel" data-cy="BouwdeelCreateUpdateHeading">
            Create or edit a Bouwdeel
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="bouwdeel-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Code" id="bouwdeel-code" name="code" data-cy="code" type="text" />
              <ValidatedField label="Omschrijving" id="bouwdeel-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                id="bouwdeel-bestaatuitVastgoedobject"
                name="bestaatuitVastgoedobject"
                data-cy="bestaatuitVastgoedobject"
                label="Bestaatuit Vastgoedobject"
                type="select"
              >
                <option value="" key="0" />
                {vastgoedobjects
                  ? vastgoedobjects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Betreft Werkbon"
                id="bouwdeel-betreftWerkbon"
                data-cy="betreftWerkbon"
                type="select"
                multiple
                name="betreftWerkbons"
              >
                <option value="" key="0" />
                {werkbons
                  ? werkbons.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bouwdeel" replace color="info">
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

export default BouwdeelUpdate;
