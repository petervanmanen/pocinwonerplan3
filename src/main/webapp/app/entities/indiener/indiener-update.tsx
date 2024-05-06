import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICollegelid } from 'app/shared/model/collegelid.model';
import { getEntities as getCollegelids } from 'app/entities/collegelid/collegelid.reducer';
import { IRaadslid } from 'app/shared/model/raadslid.model';
import { getEntities as getRaadslids } from 'app/entities/raadslid/raadslid.reducer';
import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';
import { getEntities as getRechtspersoons } from 'app/entities/rechtspersoon/rechtspersoon.reducer';
import { IRaadsstuk } from 'app/shared/model/raadsstuk.model';
import { getEntities as getRaadsstuks } from 'app/entities/raadsstuk/raadsstuk.reducer';
import { IIndiener } from 'app/shared/model/indiener.model';
import { getEntity, updateEntity, createEntity, reset } from './indiener.reducer';

export const IndienerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const collegelids = useAppSelector(state => state.collegelid.entities);
  const raadslids = useAppSelector(state => state.raadslid.entities);
  const rechtspersoons = useAppSelector(state => state.rechtspersoon.entities);
  const raadsstuks = useAppSelector(state => state.raadsstuk.entities);
  const indienerEntity = useAppSelector(state => state.indiener.entity);
  const loading = useAppSelector(state => state.indiener.loading);
  const updating = useAppSelector(state => state.indiener.updating);
  const updateSuccess = useAppSelector(state => state.indiener.updateSuccess);

  const handleClose = () => {
    navigate('/indiener');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCollegelids({}));
    dispatch(getRaadslids({}));
    dispatch(getRechtspersoons({}));
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
      ...indienerEntity,
      ...values,
      isCollegelid: collegelids.find(it => it.id.toString() === values.isCollegelid?.toString()),
      isRaadslid: raadslids.find(it => it.id.toString() === values.isRaadslid?.toString()),
      isRechtspersoon: rechtspersoons.find(it => it.id.toString() === values.isRechtspersoon?.toString()),
      heeftRaadsstuks: mapIdList(values.heeftRaadsstuks),
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
          ...indienerEntity,
          isCollegelid: indienerEntity?.isCollegelid?.id,
          isRaadslid: indienerEntity?.isRaadslid?.id,
          isRechtspersoon: indienerEntity?.isRechtspersoon?.id,
          heeftRaadsstuks: indienerEntity?.heeftRaadsstuks?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.indiener.home.createOrEditLabel" data-cy="IndienerCreateUpdateHeading">
            Create or edit a Indiener
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="indiener-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Naam" id="indiener-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="indiener-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField id="indiener-isCollegelid" name="isCollegelid" data-cy="isCollegelid" label="Is Collegelid" type="select">
                <option value="" key="0" />
                {collegelids
                  ? collegelids.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="indiener-isRaadslid" name="isRaadslid" data-cy="isRaadslid" label="Is Raadslid" type="select">
                <option value="" key="0" />
                {raadslids
                  ? raadslids.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="indiener-isRechtspersoon"
                name="isRechtspersoon"
                data-cy="isRechtspersoon"
                label="Is Rechtspersoon"
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
                label="Heeft Raadsstuk"
                id="indiener-heeftRaadsstuk"
                data-cy="heeftRaadsstuk"
                type="select"
                multiple
                name="heeftRaadsstuks"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/indiener" replace color="info">
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

export default IndienerUpdate;
