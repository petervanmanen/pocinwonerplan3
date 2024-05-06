import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBouwdeel } from 'app/shared/model/bouwdeel.model';
import { getEntities as getBouwdeels } from 'app/entities/bouwdeel/bouwdeel.reducer';
import { IWerkbon } from 'app/shared/model/werkbon.model';
import { getEntities as getWerkbons } from 'app/entities/werkbon/werkbon.reducer';
import { IBouwdeelelement } from 'app/shared/model/bouwdeelelement.model';
import { getEntity, updateEntity, createEntity, reset } from './bouwdeelelement.reducer';

export const BouwdeelelementUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bouwdeels = useAppSelector(state => state.bouwdeel.entities);
  const werkbons = useAppSelector(state => state.werkbon.entities);
  const bouwdeelelementEntity = useAppSelector(state => state.bouwdeelelement.entity);
  const loading = useAppSelector(state => state.bouwdeelelement.loading);
  const updating = useAppSelector(state => state.bouwdeelelement.updating);
  const updateSuccess = useAppSelector(state => state.bouwdeelelement.updateSuccess);

  const handleClose = () => {
    navigate('/bouwdeelelement');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBouwdeels({}));
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
      ...bouwdeelelementEntity,
      ...values,
      bestaatuitBouwdeel: bouwdeels.find(it => it.id.toString() === values.bestaatuitBouwdeel?.toString()),
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
          ...bouwdeelelementEntity,
          bestaatuitBouwdeel: bouwdeelelementEntity?.bestaatuitBouwdeel?.id,
          betreftWerkbons: bouwdeelelementEntity?.betreftWerkbons?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.bouwdeelelement.home.createOrEditLabel" data-cy="BouwdeelelementCreateUpdateHeading">
            Create or edit a Bouwdeelelement
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
                <ValidatedField name="id" required readOnly id="bouwdeelelement-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Code" id="bouwdeelelement-code" name="code" data-cy="code" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="bouwdeelelement-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                id="bouwdeelelement-bestaatuitBouwdeel"
                name="bestaatuitBouwdeel"
                data-cy="bestaatuitBouwdeel"
                label="Bestaatuit Bouwdeel"
                type="select"
              >
                <option value="" key="0" />
                {bouwdeels
                  ? bouwdeels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Betreft Werkbon"
                id="bouwdeelelement-betreftWerkbon"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bouwdeelelement" replace color="info">
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

export default BouwdeelelementUpdate;
