import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { getEntities as getKostenplaats } from 'app/entities/kostenplaats/kostenplaats.reducer';
import { ITaakveld } from 'app/shared/model/taakveld.model';
import { getEntity, updateEntity, createEntity, reset } from './taakveld.reducer';

export const TaakveldUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kostenplaats = useAppSelector(state => state.kostenplaats.entities);
  const taakveldEntity = useAppSelector(state => state.taakveld.entity);
  const loading = useAppSelector(state => state.taakveld.loading);
  const updating = useAppSelector(state => state.taakveld.updating);
  const updateSuccess = useAppSelector(state => state.taakveld.updateSuccess);

  const handleClose = () => {
    navigate('/taakveld');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getKostenplaats({}));
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
      ...taakveldEntity,
      ...values,
      heeftKostenplaats: mapIdList(values.heeftKostenplaats),
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
          ...taakveldEntity,
          heeftKostenplaats: taakveldEntity?.heeftKostenplaats?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.taakveld.home.createOrEditLabel" data-cy="TaakveldCreateUpdateHeading">
            Create or edit a Taakveld
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="taakveld-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Naam" id="taakveld-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Heeft Kostenplaats"
                id="taakveld-heeftKostenplaats"
                data-cy="heeftKostenplaats"
                type="select"
                multiple
                name="heeftKostenplaats"
              >
                <option value="" key="0" />
                {kostenplaats
                  ? kostenplaats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/taakveld" replace color="info">
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

export default TaakveldUpdate;
