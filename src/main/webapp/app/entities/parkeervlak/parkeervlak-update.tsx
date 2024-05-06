import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStraatsectie } from 'app/shared/model/straatsectie.model';
import { getEntities as getStraatsecties } from 'app/entities/straatsectie/straatsectie.reducer';
import { IParkeervlak } from 'app/shared/model/parkeervlak.model';
import { getEntity, updateEntity, createEntity, reset } from './parkeervlak.reducer';

export const ParkeervlakUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const straatsecties = useAppSelector(state => state.straatsectie.entities);
  const parkeervlakEntity = useAppSelector(state => state.parkeervlak.entity);
  const loading = useAppSelector(state => state.parkeervlak.loading);
  const updating = useAppSelector(state => state.parkeervlak.updating);
  const updateSuccess = useAppSelector(state => state.parkeervlak.updateSuccess);

  const handleClose = () => {
    navigate('/parkeervlak');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getStraatsecties({}));
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
      ...parkeervlakEntity,
      ...values,
      bevatStraatsectie: straatsecties.find(it => it.id.toString() === values.bevatStraatsectie?.toString()),
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
          ...parkeervlakEntity,
          bevatStraatsectie: parkeervlakEntity?.bevatStraatsectie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.parkeervlak.home.createOrEditLabel" data-cy="ParkeervlakCreateUpdateHeading">
            Create or edit a Parkeervlak
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="parkeervlak-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aantal" id="parkeervlak-aantal" name="aantal" data-cy="aantal" type="text" />
              <ValidatedField label="Coordinaten" id="parkeervlak-coordinaten" name="coordinaten" data-cy="coordinaten" type="text" />
              <ValidatedField label="Doelgroep" id="parkeervlak-doelgroep" name="doelgroep" data-cy="doelgroep" type="text" />
              <ValidatedField label="Fiscaal" id="parkeervlak-fiscaal" name="fiscaal" data-cy="fiscaal" check type="checkbox" />
              <ValidatedField label="Plaats" id="parkeervlak-plaats" name="plaats" data-cy="plaats" type="text" />
              <ValidatedField label="Vlakid" id="parkeervlak-vlakid" name="vlakid" data-cy="vlakid" type="text" />
              <ValidatedField
                id="parkeervlak-bevatStraatsectie"
                name="bevatStraatsectie"
                data-cy="bevatStraatsectie"
                label="Bevat Straatsectie"
                type="select"
              >
                <option value="" key="0" />
                {straatsecties
                  ? straatsecties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/parkeervlak" replace color="info">
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

export default ParkeervlakUpdate;
