import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDoelgroep } from 'app/shared/model/doelgroep.model';
import { getEntities as getDoelgroeps } from 'app/entities/doelgroep/doelgroep.reducer';
import { IMailing } from 'app/shared/model/mailing.model';
import { getEntities as getMailings } from 'app/entities/mailing/mailing.reducer';
import { IMuseumrelatie } from 'app/shared/model/museumrelatie.model';
import { getEntity, updateEntity, createEntity, reset } from './museumrelatie.reducer';

export const MuseumrelatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const doelgroeps = useAppSelector(state => state.doelgroep.entities);
  const mailings = useAppSelector(state => state.mailing.entities);
  const museumrelatieEntity = useAppSelector(state => state.museumrelatie.entity);
  const loading = useAppSelector(state => state.museumrelatie.loading);
  const updating = useAppSelector(state => state.museumrelatie.updating);
  const updateSuccess = useAppSelector(state => state.museumrelatie.updateSuccess);

  const handleClose = () => {
    navigate('/museumrelatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDoelgroeps({}));
    dispatch(getMailings({}));
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
      ...museumrelatieEntity,
      ...values,
      valtbinnenDoelgroeps: mapIdList(values.valtbinnenDoelgroeps),
      versturenaanMailings: mapIdList(values.versturenaanMailings),
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
          ...museumrelatieEntity,
          valtbinnenDoelgroeps: museumrelatieEntity?.valtbinnenDoelgroeps?.map(e => e.id.toString()),
          versturenaanMailings: museumrelatieEntity?.versturenaanMailings?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.museumrelatie.home.createOrEditLabel" data-cy="MuseumrelatieCreateUpdateHeading">
            Create or edit a Museumrelatie
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
                <ValidatedField name="id" required readOnly id="museumrelatie-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Relatiesoort" id="museumrelatie-relatiesoort" name="relatiesoort" data-cy="relatiesoort" type="text" />
              <ValidatedField
                label="Valtbinnen Doelgroep"
                id="museumrelatie-valtbinnenDoelgroep"
                data-cy="valtbinnenDoelgroep"
                type="select"
                multiple
                name="valtbinnenDoelgroeps"
              >
                <option value="" key="0" />
                {doelgroeps
                  ? doelgroeps.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Versturenaan Mailing"
                id="museumrelatie-versturenaanMailing"
                data-cy="versturenaanMailing"
                type="select"
                multiple
                name="versturenaanMailings"
              >
                <option value="" key="0" />
                {mailings
                  ? mailings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/museumrelatie" replace color="info">
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

export default MuseumrelatieUpdate;
