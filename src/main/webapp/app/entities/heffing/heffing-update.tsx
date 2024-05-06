import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHeffinggrondslag } from 'app/shared/model/heffinggrondslag.model';
import { getEntities as getHeffinggrondslags } from 'app/entities/heffinggrondslag/heffinggrondslag.reducer';
import { IHeffing } from 'app/shared/model/heffing.model';
import { getEntity, updateEntity, createEntity, reset } from './heffing.reducer';

export const HeffingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const heffinggrondslags = useAppSelector(state => state.heffinggrondslag.entities);
  const heffingEntity = useAppSelector(state => state.heffing.entity);
  const loading = useAppSelector(state => state.heffing.loading);
  const updating = useAppSelector(state => state.heffing.updating);
  const updateSuccess = useAppSelector(state => state.heffing.updateSuccess);

  const handleClose = () => {
    navigate('/heffing');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getHeffinggrondslags({}));
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
      ...heffingEntity,
      ...values,
      heeftgrondslagHeffinggrondslag: heffinggrondslags.find(it => it.id.toString() === values.heeftgrondslagHeffinggrondslag?.toString()),
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
          ...heffingEntity,
          heeftgrondslagHeffinggrondslag: heffingEntity?.heeftgrondslagHeffinggrondslag?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.heffing.home.createOrEditLabel" data-cy="HeffingCreateUpdateHeading">
            Create or edit a Heffing
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="heffing-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Bedrag" id="heffing-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField label="Code" id="heffing-code" name="code" data-cy="code" type="text" />
              <ValidatedField
                label="Datumindiening"
                id="heffing-datumindiening"
                name="datumindiening"
                data-cy="datumindiening"
                type="text"
              />
              <ValidatedField
                label="Gefactureerd"
                id="heffing-gefactureerd"
                name="gefactureerd"
                data-cy="gefactureerd"
                check
                type="checkbox"
              />
              <ValidatedField label="Inrekening" id="heffing-inrekening" name="inrekening" data-cy="inrekening" type="text" />
              <ValidatedField label="Nummer" id="heffing-nummer" name="nummer" data-cy="nummer" type="text" />
              <ValidatedField label="Runnummer" id="heffing-runnummer" name="runnummer" data-cy="runnummer" type="text" />
              <ValidatedField
                id="heffing-heeftgrondslagHeffinggrondslag"
                name="heeftgrondslagHeffinggrondslag"
                data-cy="heeftgrondslagHeffinggrondslag"
                label="Heeftgrondslag Heffinggrondslag"
                type="select"
              >
                <option value="" key="0" />
                {heffinggrondslags
                  ? heffinggrondslags.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/heffing" replace color="info">
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

export default HeffingUpdate;
