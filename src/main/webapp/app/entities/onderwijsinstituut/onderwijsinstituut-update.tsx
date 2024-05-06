import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOpleiding } from 'app/shared/model/opleiding.model';
import { getEntities as getOpleidings } from 'app/entities/opleiding/opleiding.reducer';
import { IOnderwijsinstituut } from 'app/shared/model/onderwijsinstituut.model';
import { getEntity, updateEntity, createEntity, reset } from './onderwijsinstituut.reducer';

export const OnderwijsinstituutUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const opleidings = useAppSelector(state => state.opleiding.entities);
  const onderwijsinstituutEntity = useAppSelector(state => state.onderwijsinstituut.entity);
  const loading = useAppSelector(state => state.onderwijsinstituut.loading);
  const updating = useAppSelector(state => state.onderwijsinstituut.updating);
  const updateSuccess = useAppSelector(state => state.onderwijsinstituut.updateSuccess);

  const handleClose = () => {
    navigate('/onderwijsinstituut');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getOpleidings({}));
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
      ...onderwijsinstituutEntity,
      ...values,
      wordtgegevendoorOpleidings: mapIdList(values.wordtgegevendoorOpleidings),
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
          ...onderwijsinstituutEntity,
          wordtgegevendoorOpleidings: onderwijsinstituutEntity?.wordtgegevendoorOpleidings?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.onderwijsinstituut.home.createOrEditLabel" data-cy="OnderwijsinstituutCreateUpdateHeading">
            Create or edit a Onderwijsinstituut
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
                <ValidatedField name="id" required readOnly id="onderwijsinstituut-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Wordtgegevendoor Opleiding"
                id="onderwijsinstituut-wordtgegevendoorOpleiding"
                data-cy="wordtgegevendoorOpleiding"
                type="select"
                multiple
                name="wordtgegevendoorOpleidings"
              >
                <option value="" key="0" />
                {opleidings
                  ? opleidings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/onderwijsinstituut" replace color="info">
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

export default OnderwijsinstituutUpdate;
