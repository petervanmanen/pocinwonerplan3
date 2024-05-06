import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INummeraanduiding } from 'app/shared/model/nummeraanduiding.model';
import { getEntities as getNummeraanduidings } from 'app/entities/nummeraanduiding/nummeraanduiding.reducer';
import { IBriefadres } from 'app/shared/model/briefadres.model';
import { getEntity, updateEntity, createEntity, reset } from './briefadres.reducer';

export const BriefadresUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nummeraanduidings = useAppSelector(state => state.nummeraanduiding.entities);
  const briefadresEntity = useAppSelector(state => state.briefadres.entity);
  const loading = useAppSelector(state => state.briefadres.loading);
  const updating = useAppSelector(state => state.briefadres.updating);
  const updateSuccess = useAppSelector(state => state.briefadres.updateSuccess);

  const handleClose = () => {
    navigate('/briefadres');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getNummeraanduidings({}));
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
      ...briefadresEntity,
      ...values,
      emptyNummeraanduiding: nummeraanduidings.find(it => it.id.toString() === values.emptyNummeraanduiding?.toString()),
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
          ...briefadresEntity,
          emptyNummeraanduiding: briefadresEntity?.emptyNummeraanduiding?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.briefadres.home.createOrEditLabel" data-cy="BriefadresCreateUpdateHeading">
            Create or edit a Briefadres
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="briefadres-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Adresfunctie" id="briefadres-adresfunctie" name="adresfunctie" data-cy="adresfunctie" type="text" />
              <ValidatedField label="Datumaanvang" id="briefadres-datumaanvang" name="datumaanvang" data-cy="datumaanvang" type="date" />
              <ValidatedField label="Datumeinde" id="briefadres-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField
                label="Omschrijvingaangifte"
                id="briefadres-omschrijvingaangifte"
                name="omschrijvingaangifte"
                data-cy="omschrijvingaangifte"
                type="text"
              />
              <ValidatedField
                id="briefadres-emptyNummeraanduiding"
                name="emptyNummeraanduiding"
                data-cy="emptyNummeraanduiding"
                label="Empty Nummeraanduiding"
                type="select"
              >
                <option value="" key="0" />
                {nummeraanduidings
                  ? nummeraanduidings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/briefadres" replace color="info">
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

export default BriefadresUpdate;
