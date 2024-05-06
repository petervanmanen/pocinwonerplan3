import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IResultaatsoort } from 'app/shared/model/resultaatsoort.model';
import { getEntities as getResultaatsoorts } from 'app/entities/resultaatsoort/resultaatsoort.reducer';
import { IResultaat } from 'app/shared/model/resultaat.model';
import { getEntity, updateEntity, createEntity, reset } from './resultaat.reducer';

export const ResultaatUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const resultaatsoorts = useAppSelector(state => state.resultaatsoort.entities);
  const resultaatEntity = useAppSelector(state => state.resultaat.entity);
  const loading = useAppSelector(state => state.resultaat.loading);
  const updating = useAppSelector(state => state.resultaat.updating);
  const updateSuccess = useAppSelector(state => state.resultaat.updateSuccess);

  const handleClose = () => {
    navigate('/resultaat');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getResultaatsoorts({}));
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
      ...resultaatEntity,
      ...values,
      soortresultaatResultaatsoort: resultaatsoorts.find(it => it.id.toString() === values.soortresultaatResultaatsoort?.toString()),
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
          ...resultaatEntity,
          soortresultaatResultaatsoort: resultaatEntity?.soortresultaatResultaatsoort?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.resultaat.home.createOrEditLabel" data-cy="ResultaatCreateUpdateHeading">
            Create or edit a Resultaat
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="resultaat-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datum" id="resultaat-datum" name="datum" data-cy="datum" type="text" />
              <ValidatedField label="Naam" id="resultaat-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="resultaat-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                id="resultaat-soortresultaatResultaatsoort"
                name="soortresultaatResultaatsoort"
                data-cy="soortresultaatResultaatsoort"
                label="Soortresultaat Resultaatsoort"
                type="select"
              >
                <option value="" key="0" />
                {resultaatsoorts
                  ? resultaatsoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/resultaat" replace color="info">
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

export default ResultaatUpdate;
