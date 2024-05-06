import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeverancier } from 'app/shared/model/leverancier.model';
import { getEntities as getLeveranciers } from 'app/entities/leverancier/leverancier.reducer';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { getEntities as getKostenplaats } from 'app/entities/kostenplaats/kostenplaats.reducer';
import { IOmzetgroep } from 'app/shared/model/omzetgroep.model';
import { getEntities as getOmzetgroeps } from 'app/entities/omzetgroep/omzetgroep.reducer';
import { IProductgroep } from 'app/shared/model/productgroep.model';
import { getEntities as getProductgroeps } from 'app/entities/productgroep/productgroep.reducer';
import { IDoelstelling } from 'app/shared/model/doelstelling.model';
import { getEntities as getDoelstellings } from 'app/entities/doelstelling/doelstelling.reducer';
import { IOpdrachtgever } from 'app/shared/model/opdrachtgever.model';
import { getEntities as getOpdrachtgevers } from 'app/entities/opdrachtgever/opdrachtgever.reducer';
import { IOpdrachtnemer } from 'app/shared/model/opdrachtnemer.model';
import { getEntities as getOpdrachtnemers } from 'app/entities/opdrachtnemer/opdrachtnemer.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { getEntity, updateEntity, createEntity, reset } from './product.reducer';

export const ProductUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const kostenplaats = useAppSelector(state => state.kostenplaats.entities);
  const omzetgroeps = useAppSelector(state => state.omzetgroep.entities);
  const productgroeps = useAppSelector(state => state.productgroep.entities);
  const doelstellings = useAppSelector(state => state.doelstelling.entities);
  const opdrachtgevers = useAppSelector(state => state.opdrachtgever.entities);
  const opdrachtnemers = useAppSelector(state => state.opdrachtnemer.entities);
  const productEntity = useAppSelector(state => state.product.entity);
  const loading = useAppSelector(state => state.product.loading);
  const updating = useAppSelector(state => state.product.updating);
  const updateSuccess = useAppSelector(state => state.product.updateSuccess);

  const handleClose = () => {
    navigate('/product');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeveranciers({}));
    dispatch(getKostenplaats({}));
    dispatch(getOmzetgroeps({}));
    dispatch(getProductgroeps({}));
    dispatch(getDoelstellings({}));
    dispatch(getOpdrachtgevers({}));
    dispatch(getOpdrachtnemers({}));
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
    if (values.prijs !== undefined && typeof values.prijs !== 'number') {
      values.prijs = Number(values.prijs);
    }

    const entity = {
      ...productEntity,
      ...values,
      leverancierLeverancier: leveranciers.find(it => it.id.toString() === values.leverancierLeverancier?.toString()),
      heeftKostenplaats: kostenplaats.find(it => it.id.toString() === values.heeftKostenplaats?.toString()),
      valtbinnenOmzetgroeps: mapIdList(values.valtbinnenOmzetgroeps),
      valtbinnenProductgroeps: mapIdList(values.valtbinnenProductgroeps),
      heeftDoelstelling: doelstellings.find(it => it.id.toString() === values.heeftDoelstelling?.toString()),
      isopdrachtgeverOpdrachtgever: opdrachtgevers.find(it => it.id.toString() === values.isopdrachtgeverOpdrachtgever?.toString()),
      isopdrachtnemerOpdrachtnemer: opdrachtnemers.find(it => it.id.toString() === values.isopdrachtnemerOpdrachtnemer?.toString()),
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
          ...productEntity,
          leverancierLeverancier: productEntity?.leverancierLeverancier?.id,
          heeftKostenplaats: productEntity?.heeftKostenplaats?.id,
          valtbinnenOmzetgroeps: productEntity?.valtbinnenOmzetgroeps?.map(e => e.id.toString()),
          valtbinnenProductgroeps: productEntity?.valtbinnenProductgroeps?.map(e => e.id.toString()),
          heeftDoelstelling: productEntity?.heeftDoelstelling?.id,
          isopdrachtgeverOpdrachtgever: productEntity?.isopdrachtgeverOpdrachtgever?.id,
          isopdrachtnemerOpdrachtnemer: productEntity?.isopdrachtnemerOpdrachtnemer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.product.home.createOrEditLabel" data-cy="ProductCreateUpdateHeading">
            Create or edit a Product
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="product-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Codemuseumjaarkaart"
                id="product-codemuseumjaarkaart"
                name="codemuseumjaarkaart"
                data-cy="codemuseumjaarkaart"
                type="text"
              />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="product-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField label="Datumstart" id="product-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Entreekaart" id="product-entreekaart" name="entreekaart" data-cy="entreekaart" type="text" />
              <ValidatedField label="Omschrijving" id="product-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Prijs" id="product-prijs" name="prijs" data-cy="prijs" type="text" />
              <ValidatedField
                id="product-leverancierLeverancier"
                name="leverancierLeverancier"
                data-cy="leverancierLeverancier"
                label="Leverancier Leverancier"
                type="select"
              >
                <option value="" key="0" />
                {leveranciers
                  ? leveranciers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="product-heeftKostenplaats"
                name="heeftKostenplaats"
                data-cy="heeftKostenplaats"
                label="Heeft Kostenplaats"
                type="select"
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
              <ValidatedField
                label="Valtbinnen Omzetgroep"
                id="product-valtbinnenOmzetgroep"
                data-cy="valtbinnenOmzetgroep"
                type="select"
                multiple
                name="valtbinnenOmzetgroeps"
              >
                <option value="" key="0" />
                {omzetgroeps
                  ? omzetgroeps.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Valtbinnen Productgroep"
                id="product-valtbinnenProductgroep"
                data-cy="valtbinnenProductgroep"
                type="select"
                multiple
                name="valtbinnenProductgroeps"
              >
                <option value="" key="0" />
                {productgroeps
                  ? productgroeps.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="product-heeftDoelstelling"
                name="heeftDoelstelling"
                data-cy="heeftDoelstelling"
                label="Heeft Doelstelling"
                type="select"
              >
                <option value="" key="0" />
                {doelstellings
                  ? doelstellings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="product-isopdrachtgeverOpdrachtgever"
                name="isopdrachtgeverOpdrachtgever"
                data-cy="isopdrachtgeverOpdrachtgever"
                label="Isopdrachtgever Opdrachtgever"
                type="select"
              >
                <option value="" key="0" />
                {opdrachtgevers
                  ? opdrachtgevers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="product-isopdrachtnemerOpdrachtnemer"
                name="isopdrachtnemerOpdrachtnemer"
                data-cy="isopdrachtnemerOpdrachtnemer"
                label="Isopdrachtnemer Opdrachtnemer"
                type="select"
              >
                <option value="" key="0" />
                {opdrachtnemers
                  ? opdrachtnemers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/product" replace color="info">
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

export default ProductUpdate;
