import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerzoekomtoewijzing } from 'app/shared/model/verzoekomtoewijzing.model';
import { getEntity, updateEntity, createEntity, reset } from './verzoekomtoewijzing.reducer';

export const VerzoekomtoewijzingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verzoekomtoewijzingEntity = useAppSelector(state => state.verzoekomtoewijzing.entity);
  const loading = useAppSelector(state => state.verzoekomtoewijzing.loading);
  const updating = useAppSelector(state => state.verzoekomtoewijzing.updating);
  const updateSuccess = useAppSelector(state => state.verzoekomtoewijzing.updateSuccess);

  const handleClose = () => {
    navigate('/verzoekomtoewijzing');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
      ...verzoekomtoewijzingEntity,
      ...values,
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
          ...verzoekomtoewijzingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verzoekomtoewijzing.home.createOrEditLabel" data-cy="VerzoekomtoewijzingCreateUpdateHeading">
            Create or edit a Verzoekomtoewijzing
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
                <ValidatedField name="id" required readOnly id="verzoekomtoewijzing-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Beschikkingsnummer"
                id="verzoekomtoewijzing-beschikkingsnummer"
                name="beschikkingsnummer"
                data-cy="beschikkingsnummer"
                type="text"
              />
              <ValidatedField label="Commentaar" id="verzoekomtoewijzing-commentaar" name="commentaar" data-cy="commentaar" type="text" />
              <ValidatedField
                label="Datumeindetoewijzing"
                id="verzoekomtoewijzing-datumeindetoewijzing"
                name="datumeindetoewijzing"
                data-cy="datumeindetoewijzing"
                type="date"
              />
              <ValidatedField
                label="Datumingangbeschikking"
                id="verzoekomtoewijzing-datumingangbeschikking"
                name="datumingangbeschikking"
                data-cy="datumingangbeschikking"
                type="date"
              />
              <ValidatedField
                label="Datumingangtoewijzing"
                id="verzoekomtoewijzing-datumingangtoewijzing"
                name="datumingangtoewijzing"
                data-cy="datumingangtoewijzing"
                type="date"
              />
              <ValidatedField
                label="Datumontvangst"
                id="verzoekomtoewijzing-datumontvangst"
                name="datumontvangst"
                data-cy="datumontvangst"
                type="date"
              />
              <ValidatedField label="Eenheid" id="verzoekomtoewijzing-eenheid" name="eenheid" data-cy="eenheid" type="text" />
              <ValidatedField label="Frequentie" id="verzoekomtoewijzing-frequentie" name="frequentie" data-cy="frequentie" type="text" />
              <ValidatedField
                label="Raamcontract"
                id="verzoekomtoewijzing-raamcontract"
                name="raamcontract"
                data-cy="raamcontract"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Referentieaanbieder"
                id="verzoekomtoewijzing-referentieaanbieder"
                name="referentieaanbieder"
                data-cy="referentieaanbieder"
                type="text"
              />
              <ValidatedField
                label="Soortverwijzer"
                id="verzoekomtoewijzing-soortverwijzer"
                name="soortverwijzer"
                data-cy="soortverwijzer"
                type="text"
              />
              <ValidatedField label="Verwijzer" id="verzoekomtoewijzing-verwijzer" name="verwijzer" data-cy="verwijzer" type="text" />
              <ValidatedField label="Volume" id="verzoekomtoewijzing-volume" name="volume" data-cy="volume" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verzoekomtoewijzing" replace color="info">
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

export default VerzoekomtoewijzingUpdate;
