import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAomaanvraagwmojeugd } from 'app/shared/model/aomaanvraagwmojeugd.model';
import { getEntity, updateEntity, createEntity, reset } from './aomaanvraagwmojeugd.reducer';

export const AomaanvraagwmojeugdUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aomaanvraagwmojeugdEntity = useAppSelector(state => state.aomaanvraagwmojeugd.entity);
  const loading = useAppSelector(state => state.aomaanvraagwmojeugd.loading);
  const updating = useAppSelector(state => state.aomaanvraagwmojeugd.updating);
  const updateSuccess = useAppSelector(state => state.aomaanvraagwmojeugd.updateSuccess);

  const handleClose = () => {
    navigate('/aomaanvraagwmojeugd');
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
      ...aomaanvraagwmojeugdEntity,
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
          ...aomaanvraagwmojeugdEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aomaanvraagwmojeugd.home.createOrEditLabel" data-cy="AomaanvraagwmojeugdCreateUpdateHeading">
            Create or edit a Aomaanvraagwmojeugd
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
                <ValidatedField name="id" required readOnly id="aomaanvraagwmojeugd-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Clientreactie"
                id="aomaanvraagwmojeugd-clientreactie"
                name="clientreactie"
                data-cy="clientreactie"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Datumbeschikking"
                id="aomaanvraagwmojeugd-datumbeschikking"
                name="datumbeschikking"
                data-cy="datumbeschikking"
                type="date"
              />
              <ValidatedField
                label="Datumeersteafspraak"
                id="aomaanvraagwmojeugd-datumeersteafspraak"
                name="datumeersteafspraak"
                data-cy="datumeersteafspraak"
                type="date"
              />
              <ValidatedField label="Datumeinde" id="aomaanvraagwmojeugd-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField
                label="Datumplanvastgesteld"
                id="aomaanvraagwmojeugd-datumplanvastgesteld"
                name="datumplanvastgesteld"
                data-cy="datumplanvastgesteld"
                type="date"
              />
              <ValidatedField
                label="Datumstartaanvraag"
                id="aomaanvraagwmojeugd-datumstartaanvraag"
                name="datumstartaanvraag"
                data-cy="datumstartaanvraag"
                type="date"
              />
              <ValidatedField
                label="Deskundigheid"
                id="aomaanvraagwmojeugd-deskundigheid"
                name="deskundigheid"
                data-cy="deskundigheid"
                type="text"
              />
              <ValidatedField
                label="Doorloopmethodiek"
                id="aomaanvraagwmojeugd-doorloopmethodiek"
                name="doorloopmethodiek"
                data-cy="doorloopmethodiek"
                type="text"
              />
              <ValidatedField
                label="Maximaledoorlooptijd"
                id="aomaanvraagwmojeugd-maximaledoorlooptijd"
                name="maximaledoorlooptijd"
                data-cy="maximaledoorlooptijd"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Redenafsluiting"
                id="aomaanvraagwmojeugd-redenafsluiting"
                name="redenafsluiting"
                data-cy="redenafsluiting"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aomaanvraagwmojeugd" replace color="info">
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

export default AomaanvraagwmojeugdUpdate;
