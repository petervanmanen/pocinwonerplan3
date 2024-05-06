import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';
import { getEntities as getRechtspersoons } from 'app/entities/rechtspersoon/rechtspersoon.reducer';
import { ISubsidie } from 'app/shared/model/subsidie.model';
import { getEntities as getSubsidies } from 'app/entities/subsidie/subsidie.reducer';
import { ITaak } from 'app/shared/model/taak.model';
import { getEntity, updateEntity, createEntity, reset } from './taak.reducer';

export const TaakUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rechtspersoons = useAppSelector(state => state.rechtspersoon.entities);
  const subsidies = useAppSelector(state => state.subsidie.entities);
  const taakEntity = useAppSelector(state => state.taak.entity);
  const loading = useAppSelector(state => state.taak.loading);
  const updating = useAppSelector(state => state.taak.updating);
  const updateSuccess = useAppSelector(state => state.taak.updateSuccess);

  const handleClose = () => {
    navigate('/taak');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRechtspersoons({}));
    dispatch(getSubsidies({}));
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
      ...taakEntity,
      ...values,
      projectleiderRechtspersoon: rechtspersoons.find(it => it.id.toString() === values.projectleiderRechtspersoon?.toString()),
      heeftSubsidie: subsidies.find(it => it.id.toString() === values.heeftSubsidie?.toString()),
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
          ...taakEntity,
          projectleiderRechtspersoon: taakEntity?.projectleiderRechtspersoon?.id,
          heeftSubsidie: taakEntity?.heeftSubsidie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.taak.home.createOrEditLabel" data-cy="TaakCreateUpdateHeading">
            Create or edit a Taak
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="taak-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                id="taak-projectleiderRechtspersoon"
                name="projectleiderRechtspersoon"
                data-cy="projectleiderRechtspersoon"
                label="Projectleider Rechtspersoon"
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
              <ValidatedField id="taak-heeftSubsidie" name="heeftSubsidie" data-cy="heeftSubsidie" label="Heeft Subsidie" type="select">
                <option value="" key="0" />
                {subsidies
                  ? subsidies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/taak" replace color="info">
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

export default TaakUpdate;
