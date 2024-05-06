import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Keuzebudgetbesteding from './keuzebudgetbesteding';
import KeuzebudgetbestedingDetail from './keuzebudgetbesteding-detail';
import KeuzebudgetbestedingUpdate from './keuzebudgetbesteding-update';
import KeuzebudgetbestedingDeleteDialog from './keuzebudgetbesteding-delete-dialog';

const KeuzebudgetbestedingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Keuzebudgetbesteding />} />
    <Route path="new" element={<KeuzebudgetbestedingUpdate />} />
    <Route path=":id">
      <Route index element={<KeuzebudgetbestedingDetail />} />
      <Route path="edit" element={<KeuzebudgetbestedingUpdate />} />
      <Route path="delete" element={<KeuzebudgetbestedingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KeuzebudgetbestedingRoutes;
