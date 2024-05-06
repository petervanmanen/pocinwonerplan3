import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bedrijfsproces from './bedrijfsproces';
import BedrijfsprocesDetail from './bedrijfsproces-detail';
import BedrijfsprocesUpdate from './bedrijfsproces-update';
import BedrijfsprocesDeleteDialog from './bedrijfsproces-delete-dialog';

const BedrijfsprocesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bedrijfsproces />} />
    <Route path="new" element={<BedrijfsprocesUpdate />} />
    <Route path=":id">
      <Route index element={<BedrijfsprocesDetail />} />
      <Route path="edit" element={<BedrijfsprocesUpdate />} />
      <Route path="delete" element={<BedrijfsprocesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BedrijfsprocesRoutes;
