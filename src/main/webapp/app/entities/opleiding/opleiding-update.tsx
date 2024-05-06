import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOnderwijsinstituut } from 'app/shared/model/onderwijsinstituut.model';
import { getEntities as getOnderwijsinstituuts } from 'app/entities/onderwijsinstituut/onderwijsinstituut.reducer';
import { IOpleiding } from 'app/shared/model/opleiding.model';
import { getEntity, updateEntity, createEntity, reset } from './opleiding.reducer';

export const OpleidingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const onderwijsinstituuts = useAppSelector(state => state.onderwijsinstituut.entities);
  const opleidingEntity = useAppSelector(state => state.opleiding.entity);
  const loading = useAppSelector(state => state.opleiding.loading);
  const updating = useAppSelector(state => state.opleiding.updating);
  const updateSuccess = useAppSelector(state => state.opleiding.updateSuccess);

  const handleClose = () => {
    navigate('/opleiding');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getOnderwijsinstituuts({}));
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
      ...opleidingEntity,
      ...values,
      wordtgegevendoorOnderwijsinstituuts: mapIdList(values.wordtgegevendoorOnderwijsinstituuts),
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
          ...opleidingEntity,
          wordtgegevendoorOnderwijsinstituuts: opleidingEntity?.wordtgegevendoorOnderwijsinstituuts?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.opleiding.home.createOrEditLabel" data-cy="OpleidingCreateUpdateHeading">
            Create or edit a Opleiding
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="opleiding-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Instituut" id="opleiding-instituut" name="instituut" data-cy="instituut" type="text" />
              <ValidatedField label="Naam" id="opleiding-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="opleiding-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Prijs" id="opleiding-prijs" name="prijs" data-cy="prijs" type="text" />
              <ValidatedField
                label="Wordtgegevendoor Onderwijsinstituut"
                id="opleiding-wordtgegevendoorOnderwijsinstituut"
                data-cy="wordtgegevendoorOnderwijsinstituut"
                type="select"
                multiple
                name="wordtgegevendoorOnderwijsinstituuts"
              >
                <option value="" key="0" />
                {onderwijsinstituuts
                  ? onderwijsinstituuts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/opleiding" replace color="info">
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

export default OpleidingUpdate;
